package com.example.mvp

interface BaseView<T> {

    fun setPresenter(presenter: T)

}