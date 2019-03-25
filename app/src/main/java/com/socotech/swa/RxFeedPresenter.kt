package com.socotech.swa

import com.socotech.swa.FeedContract.Presenter
import com.socotech.swa.mvp.BasePresenter
import com.socotech.swa.mvp.SchedulerProvider
import com.socotech.swa.mvp.SingleRetry
import com.socotech.swa.net.RandomUserApi
import com.socotech.swa.net.RandomUsers
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.Response
import java.util.concurrent.TimeUnit


class RxFeedPresenter(view: FeedContract.View, endpoints: RandomUserApi, scheduler: SchedulerProvider) :
    BasePresenter<FeedContract.View>(view), Presenter {

    private var endpoints = endpoints
    private var scheduler = scheduler

    override fun fetchFeed(page: Int, results: Int) {
        val obs = endpoints.get("us", page, "swa_challenge", results).singleOrError()
            .delay(1, TimeUnit.SECONDS) // app is too fast. slow it down to allow progress indicator to show.
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