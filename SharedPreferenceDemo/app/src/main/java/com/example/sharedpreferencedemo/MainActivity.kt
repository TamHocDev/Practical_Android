package com.example.sharedpreferencedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonClear: Button
    private lateinit var buttonShow: Button
    private lateinit var textViewResult: TextView

    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Log.d("MainActivity", "Setting content view")
            setContentView(R.layout.activity_main)
            Log.d("MainActivity", "Content view set successfully")

            preferenceHelper = PreferenceHelper(this)


            editTextUsername = findViewById(R.id.editTextUsername)
                ?: throw RuntimeException("Cannot find editTextUsername")

            editTextPassword = findViewById(R.id.editTextPassword)
                ?: throw RuntimeException("Cannot find editTextPassword")

            buttonSave = findViewById(R.id.buttonSave)
                ?: throw RuntimeException("Cannot find buttonSave")

            buttonClear = findViewById(R.id.buttonClear)
                ?: throw RuntimeException("Cannot find buttonClear")

            buttonShow = findViewById(R.id.buttonShow)
                ?: throw RuntimeException("Cannot find buttonShow")

            textViewResult = findViewById(R.id.textViewResult)
                ?: throw RuntimeException("Cannot find textViewResult")

            Log.d("MainActivity", "All views initialized successfully")

            buttonSave.setOnClickListener {
                val username = editTextUsername.text.toString()
                val password = editTextPassword.text.toString()

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    preferenceHelper.saveUserInfo(username, password)
                    Toast.makeText(this, "Đã lưu thông tin!", Toast.LENGTH_SHORT).show()
                    clearInputFields()
                } else {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                }
            }

            buttonClear.setOnClickListener {
                preferenceHelper.clearUserInfo()
                textViewResult.text = ""
                Toast.makeText(this, "Đã xóa thông tin!", Toast.LENGTH_SHORT).show()
                clearInputFields()
            }

            buttonShow.setOnClickListener {
                val username = preferenceHelper.getUsername()
                val password = preferenceHelper.getPassword()

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    val resultText = "Tên người dùng: $username\nMật khẩu: $password"
                    textViewResult.text = resultText
                } else {
                    textViewResult.text = "Không có thông tin được lưu!"
                }
            }

        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearInputFields() {
        editTextUsername.text.clear()
        editTextPassword.text.clear()
    }
}