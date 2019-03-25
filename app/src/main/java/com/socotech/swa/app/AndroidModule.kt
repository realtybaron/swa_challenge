package com.socotech.swa.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.socotech.swa.mvp.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(private val app: Application) {

    val context: Context
        @Provides
        @Singleton
        get() = app.applicationContext

    val schedulerProvider: SchedulerProvider
        @Provides
        @Singleton
        get() = com.socotech.swa.mvp.AndroidSchedulerProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("youworth-remote-ok", Context.MODE_PRIVATE)
    }
}