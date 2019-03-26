package com.socotech.swa

import com.socotech.swa.FeedContract.View
import com.socotech.swa.mvp.UnitTestSchedulerProvider
import com.socotech.swa.net.Info
import com.socotech.swa.net.RandomUser
import com.socotech.swa.net.RandomUserApi
import com.socotech.swa.net.RandomUsers
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
        val info = Info(1, "swa_challenge", 10, "")
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