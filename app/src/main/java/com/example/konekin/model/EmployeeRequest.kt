// Berfungsi sebagai Data Transfer Object (DTO) khusus untuk mengirimkan
// input form saat melakukan POST (Tambah) atau PUT (Edit)
package com.example.konekin.model

import com.google.gson.annotations.SerializedName

data class EmployeeRequest(
    @SerializedName("employee_name")
    val name: String,
    @SerializedName("employee_salary")
    val salary: String,
    @SerializedName("employee_age")
    val age: String
)