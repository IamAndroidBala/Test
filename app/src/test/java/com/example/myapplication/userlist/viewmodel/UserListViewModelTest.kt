package com.example.myapplication.userlist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.network.NetworkAPIService
import com.example.myapplication.userlist.model.Ad
import com.example.myapplication.userlist.model.Data
import com.example.myapplication.userlist.model.RetroResult
import com.example.myapplication.userlist.model.RetroResultUser
import com.mvvmcoroutine.retrofit.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UserListViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Mock
    lateinit var retrofit: Retrofit
    @Mock
    lateinit var apiService: NetworkAPIService
    private  var loading: Boolean = false
    lateinit var listViewModel: UserListViewModel
    private lateinit var response: Response<RetroResult>
    private lateinit var responseUser: Response<RetroResultUser>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    private val testDispatcher = TestCoroutineDispatcher()
    @Test
    fun testOnFetchUserListInfo() = testDispatcher.runBlockingTest{
        val data = Data(1,"george.bluth@reqres.in","George","Bluth","https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg")
        val ad = Ad("http://statuscode.org/","StatusCode Weekly","A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.")
        val list = arrayListOf<Data>()
        list.add(data)
        val retroResponse = RetroResult(1,6,12,2,list,ad)

        response = Response.success(retroResponse)
        listViewModel = UserListViewModel(testDispatcher,apiService)
        Mockito.`when`(apiService.fetchUsers(2)).thenReturn(response)

        listViewModel.fetchUserListInfo(2)
        println("Loading Val::::${listViewModel.fetchLoadStatus().value}")
        println("PostLive Dat::::${listViewModel.fetchUsersLiveData().value}")
        Assert.assertEquals(1,listViewModel.fetchUsersLiveData().value?.size)
        Assert.assertEquals(false, listViewModel.fetchLoadStatus().value)
    }

    @Test
    fun testOnFetchUserListInfoFailure() = testDispatcher.runBlockingTest {
        response = Response.error(404, "{\"key\":[\"somestuff\"]}".toResponseBody("application/json".toMediaTypeOrNull()))
        listViewModel = UserListViewModel(testDispatcher,apiService)
        Mockito.`when`(apiService.fetchUsers(2)).thenReturn(response)
        listViewModel.fetchUserListInfo(2)
        Assert.assertEquals("Something went wrong::Response.error()",listViewModel.fetchError().value?.toString())
        Assert.assertEquals(false, listViewModel.fetchLoadStatus().value)
    }

    @Test
    fun testOnFetchUserDetailInfo() = testDispatcher.runBlockingTest{
        val data = Data(2,"george.bluth@reqres.in","George","Bluth","https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg")
        val ad = Ad("http://statuscode.org/","StatusCode Weekly","A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.")

        val retroResponse = RetroResultUser(data,ad)
        responseUser = Response.success(retroResponse)
        listViewModel = UserListViewModel(testDispatcher,apiService)
        Mockito.`when`(apiService.fetchSelectedUsers(2)).thenReturn(responseUser)

        listViewModel.fetchUserDetailInfo(2)
        Assert.assertEquals(2,listViewModel.fetchSelectedUserLiveData().value?.id)
        Assert.assertEquals(false, listViewModel.fetchLoadStatus().value)
    }

    @Test
    fun testOnFetchUserDetailInfoFailure() = testDispatcher.runBlockingTest{
        responseUser = Response.error(404, "{\"key\":[\"somestuff\"]}".toResponseBody("application/json".toMediaTypeOrNull()))
        listViewModel = UserListViewModel(testDispatcher,apiService)
        Mockito.`when`(apiService.fetchSelectedUsers(2)).thenReturn(responseUser)
        listViewModel.fetchUserDetailInfo(2)
        Assert.assertEquals("Something went wrong::Response.error()",listViewModel.fetchError().value?.toString())
        Assert.assertEquals(false, listViewModel.fetchLoadStatus().value)
    }

    @Test
    fun testOnException() = testDispatcher.runBlockingTest{
        listViewModel = UserListViewModel(testDispatcher,apiService)
        val mockException: HttpException =  Mockito.mock(HttpException::class.java)
        Mockito.`when`(apiService.fetchUsers(2)).thenThrow(mockException)
        listViewModel.fetchUserListInfo(2)
        Assert.assertEquals(false, listViewModel.fetchLoadStatus().value)

        listViewModel = UserListViewModel(testDispatcher,apiService)
        val mockException1: HttpException =  Mockito.mock(HttpException::class.java)
        Mockito.`when`(apiService.fetchSelectedUsers(2)).thenThrow(mockException1)
        listViewModel.fetchUserDetailInfo(2)
        Assert.assertEquals(false, listViewModel.fetchLoadStatus().value)
    }

}