package com.project.storyapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.storyapps.api.ApiConfig
import com.project.storyapps.databinding.ActivityLoginBinding
import com.project.storyapps.model.LoginResult
import com.project.storyapps.model.ResponseData
import com.project.storyapps.pref.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            validation()
        }
        binding.tvRegister.setOnClickListener {
            intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        preference = UserPreference(this)

    }

    private fun validation() {
        val email = binding.editText.text.toString()
        val password = binding.edtPassword.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(this, "Password terlalu pendek", Toast.LENGTH_SHORT).show()
        } else {
            loginVerification(email, password)
        }
    }

    private fun loginVerification(email: String, password: String) {
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.loginResult
                    if (responseBody != null) {
                        initHome(responseBody)
                    } else {
                        Toast.makeText(this@LoginActivity, "Gagal Login", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.d(TAG, "onFailure : ${t.message}")
            }

        })
    }

    private fun initHome(responseBody: LoginResult) {
        saveUser(responseBody)
        intent = Intent(this@LoginActivity, StoryActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveUser(responseBody: LoginResult) {
        preference.setIsLogin(this, true)
        preference.setUser(this, responseBody)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
