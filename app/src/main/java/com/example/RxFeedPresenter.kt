package com.example

import com.example.FeedContract.Presenter
import com.example.mvp.BasePresenter
import com.example.mvp.SchedulerProvider
import com.example.mvp.SingleRetry
import com.example.net.RandomUserApi
import com.example.net.RandomUsers
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.Response

class RxFeedPresenter(
    view: FeedContract.View,
    endpoints: RandomUserApi,
    scheduler: SchedulerProvider
) : BasePresenter<FeedContract.View>(view), Presenter {

    private var endpoints = endpoints
    private var scheduler = scheduler

    override fun fetchFeed(page: Int, results: Int) {
        val obs = endpoints.get("us", page, "template-android-app", results).singleOrError()
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .retryWhen(SingleRetry(10, 1000))

        obs.subscribe(object : SingleObserver<Response<RandomUsers>> {
            override fun onSubscribe(d: Disposable) {
                addDisposable(d)
                view.setLoadingIndicator(true)
            }

            override fun onSuccess(t: Response<RandomUsers>) {
                if (t.isSuccessful) {
                    view.onFetch(t.body())
                } else {
                    val code = t.code()
                    view.onLoadingFailure("Oops! Request failed ($code). Please try again.")
                }
                view.setLoadingIndicator(false)
            }

            override fun onError(t: Throwable) {
                view.onError(t)
                view.setLoadingIndicator(false)
            }
        })
    }

}