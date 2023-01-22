package com.example.myapplication.login.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class LoginActivityTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    private var activity : LoginActivity? = null

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(LoginActivity::class.java)
            .create()
            .get()
    }

    @Test
    fun testViewsNotNull() {
        Assert.assertNotNull(activity)
    }
}