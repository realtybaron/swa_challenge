package com.android.net

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("api")
    fun get(
        @Query("nat") nat: String,
        @Query("page") page: Int,
        @Query("seed") seed: String,
        @Query("results") results: Int
    ): Observable<Response<RandomUsers>>
}