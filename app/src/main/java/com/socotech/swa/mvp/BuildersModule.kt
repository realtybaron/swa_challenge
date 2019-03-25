package com.socotech.swa.mvp

import com.socotech.swa.FeedActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    // views
    @ContributesAndroidInjector(modules = [ViewModule::class, PresenterModule::class])
    abstract fun buildFeedActivity(): FeedActivity
}
