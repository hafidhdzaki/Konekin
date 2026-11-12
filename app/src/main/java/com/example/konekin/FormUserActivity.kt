package com.example.konekin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.konekin.databinding.ActivityFormUserBinding
import com.example.konekin.model.Data
import com.example.konekin.model.EmployeeRequest
import com.example.konekin.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormUserBinding
    private var isEdit = false
    private var userId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menampilkan judul "Tambah User Baru" dengan kolom kosong,
        // lalu memanggil addUser(request) (@POST) saat tombol simpan ditekan
        isEdit = intent.getBooleanExtra("IS_EDIT", false)
        userId = intent.getIntExtra("USER_ID", -1)

        if (isEdit) {
            binding.tvTitle.text = "Edit User"
            binding.etName.setText(intent.getStringExtra("USER_NAME"))
            binding.etAge.setText(intent.getStringExtra("USER_AGE"))
            binding.etSalary.setText(intent.getStringExtra("USER_SALARY"))
            binding.btnSave.text = "Update"
        } else {
            binding.tvTitle.text = "Tambah User Baru"
            binding.btnSave.text = "Simpan"
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val age = binding.etAge.text.toString().trim()
            val salary = binding.etSalary.text.toString().trim()

            if (name.isEmpty() || age.isEmpty() || salary.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = EmployeeRequest(name, salary, age)
            if (isEdit) {
                updateUser(userId, request)
            } else {
                addUser(request)
            }
        }
    }

    private fun addUser(request: EmployeeRequest) {
        // Callback disesuaikan menjadi Callback<Data>
        ApiClient.getInstance().createUser(request).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FormUserActivity, "User berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@FormUserActivity, "Gagal menambah user: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(this@FormUserActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser(id: Int, request: EmployeeRequest) {
        // Callback disesuaikan menjadi Callback<Data>
        ApiClient.getInstance().updateUser(id, request).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FormUserActivity, "User berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@FormUserActivity, "Gagal memperbarui user: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(this@FormUserActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}