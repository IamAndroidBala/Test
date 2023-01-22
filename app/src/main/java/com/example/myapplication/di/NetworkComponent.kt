package com.example.myapplication.di

import com.example.myapplication.login.viewmodel.LoginViewModelFactory
import com.example.myapplication.userlist.viewmodel.UserListViewModelFactory
import dagger.Component
import javax.inject.Singleton

/**
 * an interface with components
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(loginViewModelFactory: LoginViewModelFactory)
    fun inject(userListViewModelFactory: UserListViewModelFactory)
}