package com.example.myapplication.userlist.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.network.NetworkAPIService
import com.example.myapplication.userlist.ItemClickListener
import com.example.myapplication.userlist.model.Data
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * view model fetches the list of user from api
 */
class UserListViewModel(private val dispatcher: CoroutineDispatcher,
                        private val apiService: NetworkAPIService): BaseViewModel<ItemClickListener>(),LifecycleObserver {

    var loading : MutableLiveData<Boolean> = MutableLiveData()
    private val errorOnAPI = MutableLiveData<String>()
    var userListMutableLiveData  = MutableLiveData<List<Data>>()
    var selectedUserMutableLiveData = MutableLiveData<Data>()
    val currentUserData = ObservableField<Data>()

    fun itemClicked(data: Data) {
        getNavigator()?.viewProfile(data)
    }

    fun fetchUserListInfo(page : Int) {
        viewModelScope.launch(dispatcher) {
            try{
                val response = apiService.fetchUsers(page)
                if(response.isSuccessful){
                    userListMutableLiveData.postValue(response.body()?.data)
                    loading.postValue(false)
                }else{
                    loading.postValue(false)
                    errorOnAPI.postValue("Something went wrong::${response.message()}")
                }

            }catch (e : Exception){
                loading.postValue(false)
                errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
            }

        }
    }

    fun fetchUserDetailInfo(id : Int ) {

        viewModelScope.launch(dispatcher) {
            try{
                val response = apiService.fetchSelectedUsers(id)
                if(response.isSuccessful){
                    selectedUserMutableLiveData.postValue(response.body()?.data)
                    loading.postValue(false)
                }else{
                    loading.postValue(false)
                    errorOnAPI.postValue("Something went wrong::${response.message()}")
                }
            }catch (e : Exception){
                loading.postValue(false)
                errorOnAPI.postValue("Something went wrong::${e.localizedMessage}")
            }
        }
    }

    fun getCurrentUser(position: Int): Data? {
        return userListMutableLiveData.value?.get(position)
    }

    fun fetchError(): LiveData<String> = errorOnAPI

    fun fetchLoadStatus(): LiveData<Boolean> = loading

    fun fetchUsersLiveData():LiveData<List<Data>> = userListMutableLiveData

    fun fetchSelectedUserLiveData():LiveData<Data> = selectedUserMutableLiveData

}