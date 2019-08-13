package com.android.mvp

import com.android.FeedContract
import com.android.RxFeedPresenter
import com.android.net.RandomUserApi
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
