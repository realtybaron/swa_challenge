package com.example.mvp

import com.example.FeedContract
import com.example.RxFeedPresenter
import com.example.net.RandomUserApi
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    internal fun provideFeedPresenter(
        view: FeedContract.View,
        remoteOK: RandomUserApi,
        schedulerProvider: SchedulerProvider
    ): FeedContract.Presenter {
        return RxFeedPresenter(view, remoteOK, schedulerProvider)
    }

}
