package com.socotech.swa.net;

import com.socotech.swa.mvp.UnitTestSchedulerProvider;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ApiEndpointTest {

    private RandomUserApi randomUserApi;

    @Before
    public void onSetup() {
        MockitoAnnotations.initMocks(this);
        randomUserApi = new NetModule().api(new UnitTestSchedulerProvider());
    }

    @Test
    public void testGet() {
        TestObserver<Response<RandomUsers>> observable = randomUserApi.get("us", 1, "swa_challenge", 100).test();
        observable.assertNoErrors();
    }

}