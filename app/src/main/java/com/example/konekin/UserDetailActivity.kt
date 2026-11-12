package com.example.konekin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.konekin.databinding.ActivityUserDetailBinding
import com.example.konekin.model.Data
import com.example.konekin.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menangkap ID karyawan yang dikirim dari MainActivity dan memvalidasinya sebelum memanggil API
        userId = intent.getIntExtra("USER_ID", -1)
        if (userId != -1) {
            //   Memanggil getUserById(id) ke endpoint /employees/{id} dan
            //   menampilkan hasil respons ke masing-masing TextView
            fetchUserDetail(userId)
        } else {
            Toast.makeText(this, "ID User tidak valid", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Membuka FormUserActivity dengan membawa flag IS_EDIT = true beserta
        // data nama, umur, dan gaji saat ini agar form langsung terisi otomatis
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, FormUserActivity::class.java).apply {
                putExtra("IS_EDIT", true)
                putExtra("USER_ID", userId)
                putExtra("USER_NAME", binding.tvName.text.toString().replace("Name: ", ""))
                putExtra("USER_AGE", binding.tvAge.text.toString().replace("Age: ", ""))
                putExtra("USER_SALARY", binding.tvSalary.text.toString().replace("Salary: ", ""))
            }
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            deleteUser(userId)
        }
    }

    private fun fetchUserDetail(id: Int) {
        ApiClient.getInstance().getUserById(id).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    binding.tvId.text = "ID: ${user.id}"
                    binding.tvName.text = "Name: ${user.employeeName}"
                    binding.tvAge.text = "Age: ${user.employeeAge}"
                    binding.tvSalary.text = "Salary: ${user.employeeSalary}"
                } else {
                    Toast.makeText(this@UserDetailActivity, "Gagal memuat detail user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(this@UserDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteUser(id: Int) {
        ApiClient.getInstance().deleteUser(id).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UserDetailActivity, "User berhasil dihapus", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@UserDetailActivity, "Gagal menghapus user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(this@UserDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}