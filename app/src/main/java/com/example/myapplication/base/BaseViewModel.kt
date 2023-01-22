package com.example.myapplication.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

open class BaseViewModel<N> : ViewModel() {

    private lateinit var mNavigator: WeakReference<N>

    open fun getNavigator(): N? {
        return mNavigator.get()
    }

    open fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }

}