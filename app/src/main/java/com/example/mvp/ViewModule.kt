package com.example.mvp

import com.example.FeedActivity
import com.example.FeedContract
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModule {
    @Binds
    internal abstract fun provideFeedView(activity: FeedActivity): FeedContract.View
}
