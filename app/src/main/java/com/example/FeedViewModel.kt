package com.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.net.RandomUser
import com.example.net.RandomUsers

class FeedViewModel(
    var randomUsers: MutableList<RandomUser> = mutableListOf()
) : ViewModel(), FeedContract.View {

    var fail: String? = null
    var error: Throwable? = null
    val state: MutableLiveData<State> = MutableLiveData(State.WillLoad)

    override fun onError(t: Throwable) {
        error = t
        state.value = State.DidError
    }

    override fun onFetch(t: RandomUsers?) {
        t?.let {
            for (user in t.results) {
                randomUsers.add(user)
            }
        }
        state.value = State.DidLoad
    }

    override fun onLoadingFailure(t: String) {
        state.value = State.DidFail
    }

    override fun setLoadingIndicator(t: Boolean) {
        if (t) {
            state.value = State.WillLoad
        }
    }

    enum class State {
        DidFail,
        DidLoad,
        DidError,
        WillLoad
    }


    companion object {
        fun create(): FeedViewModel {
            return FeedViewModel()
        }
    }
}