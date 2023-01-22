package com.example.myapplication.login.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginFragmentTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    lateinit var  loginFragment: LoginFragment

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        loginFragment = LoginFragment()
    }

    @Test
    fun testFragment(){

    }
}