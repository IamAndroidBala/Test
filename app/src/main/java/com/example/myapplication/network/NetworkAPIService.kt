package com.example.myapplication.network

import com.example.myapplication.login.model.LoginModel
import com.example.myapplication.login.model.TokenModel
import com.example.myapplication.userlist.model.RetroResult
import com.example.myapplication.userlist.model.RetroResultUser
import retrofit2.Response
import retrofit2.http.*

interface NetworkAPIService {

    @POST("/api/login")
    suspend fun validateLogin(@Body loginModel: LoginModel) : Response<TokenModel>

    @GET("/api/users")
    suspend fun fetchUsers(@Query("page") page :Int): Response<RetroResult>

    @GET("/api/users/{id}")
    suspend fun fetchSelectedUsers(@Path("id") id : Int): Response<RetroResultUser>
}