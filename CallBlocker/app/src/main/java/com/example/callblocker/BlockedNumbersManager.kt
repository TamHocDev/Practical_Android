package com.example.callblocker

import android.content.Context
import android.content.SharedPreferences

class BlockedNumbersManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("BlockedNumbers", Context.MODE_PRIVATE)

    fun addNumber(number: String) {
        val editor = sharedPreferences.edit()
        editor.putString(number, number)
        editor.apply()
    }

    fun removeNumber(number: String) {
        val editor = sharedPreferences.edit()
        editor.remove(number)
        editor.apply()
    }

    fun isNumberBlocked(number: String?): Boolean {
        return sharedPreferences.contains(number)
    }

    fun getBlockedNumbers(): Set<String> {
        return sharedPreferences.all.keys
    }
}