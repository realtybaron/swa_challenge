package com.example.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.mvp.AndroidSchedulerProvider
import com.example.mvp.SchedulerProvider
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
        get() = AndroidSchedulerProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("template-android-app", Context.MODE_PRIVATE)
    }
}
