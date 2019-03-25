package com.socotech.swa.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class RandomUserApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerRandomUserComponent.builder().androidModule(AndroidModule(this)).create(this);
    }

}
