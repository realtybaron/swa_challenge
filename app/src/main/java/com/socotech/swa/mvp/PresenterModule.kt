package com.socotech.swa.mvp

import com.socotech.swa.FeedContract
import com.socotech.swa.RxFeedPresenter
import com.socotech.swa.net.RandomUserApi
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
