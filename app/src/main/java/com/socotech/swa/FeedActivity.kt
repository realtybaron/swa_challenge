package com.socotech.swa

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.socotech.swa.net.RandomUsers
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
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

        super.setSupportActionBar(toolbar)

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
        adapter = FeedAdapter()
        recycler.adapter = adapter
        // listen for refresh swipes
        swipe_refresh.setOnRefreshListener(this)
        // fetch
        presenter.fetchFeed(page++, maxResults)
    }

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

    override fun onError(t: Throwable) {
        Snackbar.make(recycler, t.localizedMessage, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    override fun onLoadingFailure(t: String) {
        Snackbar.make(recycler, t, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    override fun setLoadingIndicator(t: Boolean) {
        if (!t) {
            progress.visibility = View.GONE
        } else if (adapter.isEmpty()) {
            progress.visibility = View.VISIBLE
        }
    }

    override fun onRefresh() {
        page = 1
        adapter.clear()
        presenter.fetchFeed(page++, maxResults)
    }
}
