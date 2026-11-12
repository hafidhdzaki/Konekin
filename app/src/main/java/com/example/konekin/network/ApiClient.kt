package com.example.konekin.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
// Mengonversi respons teks JSON dari server menjadi objek data class Kotlin secara otomatis (dan sebaliknya)
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getInstance(): ApiService {
        // Mencatat seluruh detail request dan response HTTP ke dalam Logcat untuk mempermudah proses debugging
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        // Mesin HTTP client yang menjalankan request jaringan sekaligus memasang interceptor logging
        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        val builder = Retrofit.Builder()
            // Menetapkan alamat server utama sebagai akar dari seluruh endpoint
            .baseUrl("https://6af496b255a1de7dcf0448cb.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
        return builder.create(ApiService::class.java)
    }
}