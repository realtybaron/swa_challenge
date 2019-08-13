package com.android.mvp

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Provides different types of schedulers.
 */
@Singleton
class UnitTestSchedulerProvider : SchedulerProvider {

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

}
