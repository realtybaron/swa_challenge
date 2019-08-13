package com.android.mvp

import com.android.FeedActivity
import com.android.FeedContract
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModule {
    @Binds
    internal abstract fun provideFeedView(activity: FeedActivity): FeedContract.View
}
