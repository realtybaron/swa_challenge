package com.example.mvp

import io.reactivex.Flowable
import io.reactivex.functions.Function

import java.util.concurrent.TimeUnit

class SingleRetry(private val maxRetries: Int, private val retryDelayMillis: Int) :
    Function<Flowable<out Throwable>, Flowable<*>> {

    private var retryCount: Int = 0

    init {
        this.retryCount = 0
    }

    override fun apply(attempts: Flowable<out Throwable>): Flowable<*> {
        return attempts.flatMap { throwable ->
            if (++retryCount < maxRetries) {
                // When this Observable calls onNext, the original
                // Observable will be retried (i.e. re-subscribed).
                Flowable.timer(retryDelayMillis.toLong(), TimeUnit.MILLISECONDS)
            } else Flowable.error(throwable)
            // Max retries hit. Just pass the error along.
        }
    }
}
