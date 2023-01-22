package com.example.myapplication.login.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.login.model.LoginModel
import com.example.myapplication.login.model.TokenModel
import com.example.myapplication.network.NetworkAPIService
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
class LoginViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Mock
    lateinit var retrofit: Retrofit
    @Mock
    lateinit var apiService: NetworkAPIService
    lateinit var loginViewModel: LoginViewModel
    private lateinit var response:Response<TokenModel>
    val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testOnLoginSuccess() = testDispatcher.runBlockingTest{
        val loginModel = LoginModel("eve.holt@reqres.in","cityslicka")
        loginViewModel = LoginViewModel(testDispatcher,apiService)
        response = Response.success(TokenModel("QpwL5tke4Pnpja7X4",""))
        Mockito.`when`(apiService.validateLogin(loginModel)).thenReturn(response)

        loginViewModel.validateLogin(loginModel)
        println("Loading Val::::${loginViewModel.fetchLoadStatus().value}")
        println("PostLive Dat::::${loginViewModel.fetchTokenStatus().value}")
        Assert.assertEquals("QpwL5tke4Pnpja7X4",loginViewModel.fetchTokenStatus().value?.token)
        Assert.assertEquals(false, loginViewModel.fetchLoadStatus().value)
    }

    @Test
    fun testOnInvalidLogin() = testDispatcher.runBlockingTest {
        val loginModel = LoginModel("eve.holt@reqre.in","cityslicka")
        loginViewModel = LoginViewModel(testDispatcher,apiService)
        response = Response.success(TokenModel("","user Not Found"))
        Mockito.`when`(apiService.validateLogin(loginModel)).thenReturn(response)
        loginViewModel.validateLogin(loginModel)
        println("Loading Val::::${loginViewModel.fetchLoadStatus().value}")
        println("PostLive Dat::::${loginViewModel.fetchTokenStatus().value}")
        Assert.assertEquals("user Not Found",loginViewModel.fetchTokenStatus().value?.error)
        Assert.assertEquals(false, loginViewModel.fetchLoadStatus().value)
    }

    @Test
    fun testOnLoginFailure() = testDispatcher.runBlockingTest{
        val loginModel = LoginModel("eve.holt@reqre.in","cityslicka")
        response = Response.error(404, "{\"key\":[\"somestuff\"]}".toResponseBody("application/json".toMediaTypeOrNull()))
        loginViewModel = LoginViewModel(testDispatcher,apiService)
        Mockito.`when`(apiService.validateLogin(loginModel)).thenReturn(response)
        loginViewModel.validateLogin(loginModel)
        Assert.assertEquals("Something went wrong::Method get in org.json.JSONObject not mocked. See http://g.co/androidstudio/not-mocked for details.",loginViewModel.fetchError().value?.toString())
        Assert.assertEquals(false, loginViewModel.fetchLoadStatus().value)
    }

    @Test
    fun testOnException() = testDispatcher.runBlockingTest {
        val loginModel = LoginModel("eve.holt@reqre.in","cityslicka")
        loginViewModel = LoginViewModel(testDispatcher, apiService)
        val mockException: HttpException = Mockito.mock(HttpException::class.java)
        Mockito.`when`(apiService.validateLogin(loginModel)).thenThrow(mockException)
        loginViewModel.validateLogin(loginModel)
        Assert.assertEquals(false, loginViewModel.fetchLoadStatus().value)
    }

}