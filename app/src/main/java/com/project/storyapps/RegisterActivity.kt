package com.project.storyapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.storyapps.api.ApiConfig
import com.project.storyapps.databinding.ActivityRegisterBinding
import com.project.storyapps.model.ResponseRegister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener { validation() }
    }

    private fun validation() {
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "email tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(this@RegisterActivity, "password terlalu sedikit", Toast.LENGTH_SHORT)
                .show()
        } else {
            toRegister(name,email,password)
        }
    }

    private fun toRegister(name: String, email: String, password: String) {
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity,"Berhasil Terdaftar", Toast.LENGTH_SHORT).show()
                    intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Log.d(TAG, "onFailure : ${t.message}")
            }

        })
    }

    companion object{
        private const val TAG = "RegisterActivity"
    }
}