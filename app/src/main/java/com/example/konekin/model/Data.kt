// berisi Data Class yang menjadi cetakan untuk membaca data
// dari server maupun mengirim data ke server.
package com.example.konekin.model

import com.google.gson.annotations.SerializedName

data class Data(
    // Menjembatani perbedaan penamaan antara key JSON di server
    // (format snake_case seperti employee_name) dengan properti variabel
    // di Kotlin (format camelCase seperti employeeName)
    @SerializedName("id")
    val id: Int,
    @SerializedName("employee_name")
    val employeeName: String?,
    @SerializedName("employee_salary")
    val employeeSalary: Int?,
    @SerializedName("employee_age")
    val employeeAge: Int?,
    @SerializedName("profile_image")
    // Mencegah aplikasi force close (NullPointerException) apabila ada field data dari server yang bernilai kosong atau null.
    val profileImage: String? = null
)