package com.example

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import javax.inject.Inject

class FeedActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var page = 1
    private var maxResults = 10
    private lateinit var adapter: FeedAdapter
    private lateinit var viewModel: FeedViewModel

    @Inject
    lateinit var presenter: FeedContract.Presenter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedState: Bundle?) {
        // first inject...
        AndroidInjection.inject(this)
        // ...then create
        super.onCreate(savedState)

        super.setContentView(R.layout.content_main)

        // get view model
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)
        // observe view model
        viewModel.state.observe(this, Observer {
            it?.let {
                when (it) {
                    FeedViewModel.State.DidFail -> {
                        // hide progress
                        progress.visibility = View.GONE
                        // show snack bar
                        Snackbar.make(recycler, viewModel.fail!!, Snackbar.LENGTH_LONG).show()
                    }
                    FeedViewModel.State.DidLoad -> {
                        // hide progress
                        progress.visibility = View.GONE
                        // add results to adapter
                        for (result in viewModel.randomUsers) {
                            adapter.add(result)
                        }
                        // got items to show?
                        if (adapter.itemCount != 0) {
                            empty.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        } else {
                            empty.visibility = View.VISIBLE
                        }
                        swipe_refresh.isRefreshing = false
                    }
                    FeedViewModel.State.DidError -> {
                        // hide progress
                        progress.visibility = View.GONE
                        // show snack bar
                        Snackbar.make(
                            recycler,
                            viewModel.error!!.localizedMessage,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    FeedViewModel.State.WillLoad -> {
                        if (!adapter.isEmpty()) {
                            progress.visibility = View.GONE
                        } else {
                            progress.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true)

        // use a linear layout manager
        val layoutManager = recycler.layoutManager as LinearLayoutManager
        recycler.addItemDecoration(
            DividerItemDecoration(
                recycler.context,
                layoutManager.orientation
            )
        )
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
            val intent = Intent(ctx, com.example.DetailActivity::class.java).putExtra(
                "user",
                it.tag as Parcelable
            )
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
    }

    override fun onResume() {
        super.onResume()
        // fetch?
        if (adapter.isEmpty()) {
            presenter.fetchFeed(page++, maxResults)
        }
    }

    // called when user pulls to refresh
    override fun onRefresh() {
        page = 1
        adapter.clear()
        presenter.fetchFeed(page++, maxResults)
    }
}
