package com.example.konekin.network

import com.example.konekin.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // GET All langsung mengembalikan List<Data>
    @GET("employees")
    fun getAllUsers(): Call<List<Data>>

    // GET By ID langsung mengembalikan objek Data
    @GET("employees/{id}")
    // menyisipkan nilai variabel id ke dalam {id} pada URL
    fun getUserById(@Path("id") id: Int): Call<Data>

    // POST/Create mengembalikan objek Data yang baru dibuat
    @POST("employees")
    // mengubah objek EmployeeRequest menjadi payload JSON
    fun createUser(@Body request: EmployeeRequest): Call<Data>

    // PUT/Update mengembalikan objek Data yang diperbarui
    @PUT("employees/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Body request: EmployeeRequest
    ): Call<Data>

    // DELETE mengembalikan objek Data yang dihapus
    @DELETE("employees/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Data>
}