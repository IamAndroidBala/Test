package com.example.myapplication.userlist.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.login.view.LoginFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListDetailFragmentTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    lateinit var  userListFragment: UserListDetailFragment

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        userListFragment = UserListDetailFragment()
    }

    @Test
    fun testFragment(){

    }
}