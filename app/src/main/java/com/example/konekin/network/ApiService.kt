package com.example.konekin.network

import com.example.konekin.model.Users
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    // 	https://dummy.restapiexample.com/api/v1/employees
    @GET("employees")
    fun getAllUsers(): Call<Users>
}