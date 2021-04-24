package com.example.mylib_test.activity.lifecircle.helper

interface IFragmentLife {
    fun onFirstVisibility()
    fun onFragmentVisibilityChanged(isVisibleToUser: Boolean, mode: Mode)
}