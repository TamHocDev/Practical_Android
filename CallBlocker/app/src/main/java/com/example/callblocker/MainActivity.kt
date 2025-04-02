package com.example.callblocker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonAddNumber: Button
    private lateinit var listViewBlockedNumbers: ListView
    private lateinit var blockedNumbersManager: BlockedNumbersManager
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        buttonAddNumber = findViewById(R.id.buttonAddNumber)
        listViewBlockedNumbers = findViewById(R.id.listViewBlockedNumbers)
        blockedNumbersManager = BlockedNumbersManager(this)

        val blockedNumbers = blockedNumbersManager.getBlockedNumbers().toList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, blockedNumbers)
        listViewBlockedNumbers.adapter = adapter

        buttonAddNumber.setOnClickListener {
            val phoneNumber = editTextPhoneNumber.text.toString()
            blockedNumbersManager.addNumber(phoneNumber)
            adapter.add(phoneNumber)
            adapter.notifyDataSetChanged()
            editTextPhoneNumber.text.clear()
        }
    }
}