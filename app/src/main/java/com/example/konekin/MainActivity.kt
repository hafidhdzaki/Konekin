package com.example.konekin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.konekin.databinding.ActivityMainBinding
import com.example.konekin.model.Data
import com.example.konekin.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // Menyimpan seluruh objek Data secara utuh di memori agar
    // ketika pengguna mengklik salah satu nama pada ListView,
    // aplikasi bisa mengambil selectedUser.id untuk dikirimkan ke halaman detail
    private val rawDataList = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Membuka FormUserActivity dalam mode tambah data baru
        binding.btnAddUser.setOnClickListener {
            val intent = Intent(this, FormUserActivity::class.java)
            startActivity(intent)
        }

        // Menangani klik item daftar dan membuka UserDetailActivity sambil membawa USER_ID melalui Intent
        binding.lvNama.setOnItemClickListener { _, _, position, _ ->
            if (position in rawDataList.indices) {
                val selectedUser = rawDataList[position]
                val intent = Intent(this, UserDetailActivity::class.java).apply {
                    putExtra("USER_ID", selectedUser.id)
                }
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val client = ApiClient.getInstance()

        client.getAllUsers().enqueue(object : Callback<List<Data>> {
            override fun onResponse(call: Call<List<Data>>, response: Response<List<Data>>) {
                if (response.isSuccessful && response.body() != null) {
                    rawDataList.clear()
                    val userList = ArrayList<String>()
                    val listData = response.body()!!

                    rawDataList.addAll(listData)
                    for (i in listData) {
                        userList.add(i.employeeName ?: "(Tanpa Nama)")
                    }

                    val listAdapter = ArrayAdapter(
                        this@MainActivity,
                        android.R.layout.simple_list_item_1,
                        userList
                    )
                    binding.lvNama.adapter = listAdapter
                } else {
                    // Menampilkan Toast jika respon server mengembalikan HTTP 404/500/dll
                    Toast.makeText(
                        this@MainActivity,
                        "Gagal memuat data: HTTP ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Data>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}