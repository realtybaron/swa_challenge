package com.example

import com.android.FeedContract.View
import com.android.RxFeedPresenter
import com.android.net.Info
import com.android.net.RandomUser
import com.android.net.RandomUserApi
import com.android.net.RandomUsers
import com.example.mvp.UnitTestSchedulerProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class FeedPresenterTest {

    @Mock
    lateinit var view: View
    @Mock
    lateinit var endpoints: RandomUserApi

    lateinit var presenter: RxFeedPresenter

    @Before
    fun onSetup() {
        MockitoAnnotations.initMocks(this)
        presenter = RxFeedPresenter(view, endpoints, UnitTestSchedulerProvider())
    }

    @Test
    @Throws(Exception::class)
    fun testGetConfig() {
        val info = Info(1, "template-android-app", 10, "")
        val list = ArrayList<RandomUser>()
        val body = RandomUsers(info, list)
        val response = Response.success(body)
        val observable = Observable.just(response)
        Mockito.`when`(endpoints.get(anyString(), anyInt(), anyString(), anyInt())).thenReturn(observable)
        // execute
        presenter.fetchFeed(1, 10)
        // verify
        verify<View>(view, times(1)).setLoadingIndicator(true)
        verify<View>(view, times(1)).onFetch(body)
        verify<View>(view, times(1)).setLoadingIndicator(false)
    }
}