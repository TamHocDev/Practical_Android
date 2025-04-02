package com.example.sharedpreferencedemo

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferenceHelper(context: Context) {

    private val PREF_NAME = "UserPrefs"
    private val KEY_USERNAME = "username"
    private val KEY_PASSWORD = "password"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Lưu thông tin người dùng
    fun saveUserInfo(username: String, password: String) {
        try {
            val editor = sharedPreferences.edit()
            editor.putString(KEY_USERNAME, username)
            editor.putString(KEY_PASSWORD, password)
            editor.apply()
            Log.d("PreferenceHelper", "User info saved successfully")
        } catch (e: Exception) {
            Log.e("PreferenceHelper", "Error saving user info", e)
        }
    }

    // Lấy tên người dùng
    fun getUsername(): String {
        return try {
            sharedPreferences.getString(KEY_USERNAME, "") ?: ""
        } catch (e: Exception) {
            Log.e("PreferenceHelper", "Error getting username", e)
            ""
        }
    }

    // Lấy mật khẩu
    fun getPassword(): String {
        return try {
            sharedPreferences.getString(KEY_PASSWORD, "") ?: ""
        } catch (e: Exception) {
            Log.e("PreferenceHelper", "Error getting password", e)
            ""
        }
    }

    // Xóa tất cả thông tin
    fun clearUserInfo() {
        try {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            Log.d("PreferenceHelper", "User info cleared successfully")
        } catch (e: Exception) {
            Log.e("PreferenceHelper", "Error clearing user info", e)
        }
    }
}