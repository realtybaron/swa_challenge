package com.example.mvp

import com.example.FeedActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    // views
    @ContributesAndroidInjector(modules = [ViewModule::class, PresenterModule::class])
    abstract fun buildFeedActivity(): FeedActivity
}
