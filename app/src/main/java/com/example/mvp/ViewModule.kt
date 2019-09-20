package com.example.mvp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.FeedContract
import com.example.FeedViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModule {

    private val feedViewModel: FeedViewModel = FeedViewModel()

    @Provides
    internal fun provideFeedContractView(): FeedContract.View {
        return feedViewModel
    }

    @Provides
    internal fun provideViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
                    feedViewModel as T
                } else {
                    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
                }
            }
        }
    }

}
