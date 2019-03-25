package com.socotech.swa.mvp

import com.socotech.swa.FeedActivity
import com.socotech.swa.FeedContract
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModule {
    @Binds
    internal abstract fun provideFeedView(activity: FeedActivity): FeedContract.View
}
