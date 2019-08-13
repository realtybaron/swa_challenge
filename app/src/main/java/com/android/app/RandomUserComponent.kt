package com.android.app

import com.android.mvp.BuildersModule
import com.android.net.NetModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, AndroidModule::class, BuildersModule::class, AndroidSupportInjectionModule::class])
interface RandomUserComponent : AndroidInjector<RandomUserApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RandomUserApp>() {
        internal abstract fun netModule(nm: NetModule): Builder

        internal abstract fun androidModule(am: AndroidModule): Builder
    }
}
