package com.example.myapplication.login.viewmodel

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.login.LoginListener
import com.example.myapplication.login.model.LoginModel
import com.example.myapplication.login.model.TokenModel
import com.example.myapplication.network.NetworkAPIService
import com.example.myapplication.userlist.ItemClickListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * login view model will check the login status
 * if login credential is correct then response should be fine
 */
class LoginViewModel(private val dispatcher: CoroutineDispatcher, private val apiService: NetworkAPIService) : BaseViewModel<LoginListener>(), LifecycleObserver {

    var loading : MutableLiveData<Boolean> = MutableLiveData()
    private val errorOnAPI = MutableLiveData<String>()
    var responseToken : MutableLiveData<TokenModel> = MutableLiveData()
    val userName = ObservableField<String>()
    val password = ObservableField<String>()

    fun onClickLogin(){
        getNavigator()!!.login()
    }

     @SuppressLint("SuspiciousIndentation")
     fun validateLogin(loginModel: LoginModel){
         viewModelScope.launch(dispatcher) {
             try{
               val response = apiService.validateLogin(loginModel)
                 if(response.isSuccessful) {
                     responseToken.postValue(response.body())
                     loading.postValue(false)
                 }else{
                     loading.postValue(false)
                     val errorMessage  = response.errorBody()?.string()?.let { JSONObject(it).get("error").toString() }
                     errorOnAPI.postValue("$errorMessage")
                 }

             }catch (e : Exception){
                 loading.postValue(false)
                 errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
             }
         }
     }

    fun fetchError(): LiveData<String> = errorOnAPI
    fun fetchLoadStatus(): LiveData<Boolean> = loading
    fun fetchTokenStatus():LiveData<TokenModel>  = responseToken

}