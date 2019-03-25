package com.socotech.swa.mvp;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Singleton;

/**
 * Provides different types of schedulers.
 */
@Singleton
public class UnitTestSchedulerProvider implements SchedulerProvider {

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }

}
