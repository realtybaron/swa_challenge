package com.android

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.net.RandomUsers
import com.google.android.material.snackbar.Snackbar
import com.socotech.swa.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import javax.inject.Inject


class FeedActivity : AppCompatActivity(), FeedContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var page = 1
    private var maxResults = 10
    private lateinit var adapter: FeedAdapter

    @Inject
    lateinit var presenter: FeedContract.Presenter

    override fun onCreate(savedState: Bundle?) {
        // first inject...
        AndroidInjection.inject(this)
        // ...then create
        super.onCreate(savedState)

        super.setContentView(R.layout.content_main)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true)

        // use a linear layout manager
        val layoutManager = recycler.layoutManager as LinearLayoutManager
        recycler.addItemDecoration(DividerItemDecoration(recycler.context, layoutManager.orientation))
        // listen for scrolling
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(view, dx, dy)
                // fetch more items?
                val llm = recycler.layoutManager as LinearLayoutManager
                val totalItems = llm.itemCount
                val visibleItems = llm.childCount
                val firstVisibleItem = llm.findFirstVisibleItemPosition()
                if (visibleItems + firstVisibleItem >= totalItems && firstVisibleItem >= 0) {
                    presenter.fetchFeed(page++, maxResults)
                }
            }
        })
        // set adapter on view
        adapter = FeedAdapter(View.OnClickListener {
            val ctx = it?.context
            val intent = Intent(ctx, DetailActivity::class.java).putExtra("user", it.tag as Parcelable)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ctx?.startActivity(intent)
            } else {
                val name = Pair.create<View, String>(it?.name, "name")
                val avatar = Pair.create<View, String>(it?.avatar, "avatar")
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, name, avatar)
                ctx?.startActivity(intent, options.toBundle())
            }

        })
        recycler.adapter = adapter
        // listen for refresh swipes
        swipe_refresh.setOnRefreshListener(this)
        // fetch
        presenter.fetchFeed(page++, maxResults)
    }

    // called by presenter when fetch succeeds
    override fun onFetch(t: RandomUsers?) {
        if (t != null) {
            // add results to adapter
            for (result in t.results) {
                adapter.add(result)
            }
            // got items to show?
            if (adapter.itemCount != 0) {
                empty.visibility = View.GONE
                adapter.notifyDataSetChanged()
            } else {
                empty.visibility = View.VISIBLE
            }
        }
        swipe_refresh.isRefreshing = false
    }

    // called by presenter when error occurs
    override fun onError(t: Throwable) {
        Snackbar.make(recycler, t.localizedMessage, Snackbar.LENGTH_LONG).show()
    }

    // called by presenter when loading fails
    override fun onLoadingFailure(t: String) {
        Snackbar.make(recycler, t, Snackbar.LENGTH_LONG).show()
    }

    // called by presenter when loading
    override fun setLoadingIndicator(t: Boolean) {
        if (!t) {
            progress.visibility = View.GONE
        } else if (adapter.isEmpty()) {
            progress.visibility = View.VISIBLE
        }
    }

    // called when user pulls to refresh
    override fun onRefresh() {
        page = 1
        adapter.clear()
        presenter.fetchFeed(page++, maxResults)
    }
}
