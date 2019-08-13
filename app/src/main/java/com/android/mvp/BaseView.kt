package com.android.mvp

interface BaseView<T> {

    fun setPresenter(presenter: T)

}