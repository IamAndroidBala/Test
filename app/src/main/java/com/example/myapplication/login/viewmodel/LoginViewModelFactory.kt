package com.example.myapplication.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.di.DaggerNetworkComponent
import com.example.myapplication.network.NetworkAPIService
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Inject

class LoginViewModelFactory : ViewModelProvider.Factory {
    @Inject
    lateinit var retrofit: Retrofit
    lateinit var networkAPIService: NetworkAPIService

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        DaggerNetworkComponent.create().inject(this)
        networkAPIService = retrofit.create(NetworkAPIService::class.java)
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(Dispatchers.Main,networkAPIService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}