package com.example.myapplication.userlist.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListActivityTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()
    lateinit var  userListActivity: UserListActivity

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        userListActivity = UserListActivity()
    }

    @Test
    fun testActivity(){
        Assert.assertNotNull(userListActivity)
    }

}