package com.example.contactapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import android.widget.Button
import com.example.contactapp.Adapter.ContactAdapter
import com.example.contactapp.Model.ContactModel
import java.util.ArrayList
import androidx.appcompat.app.AppCompatActivity

import com.example.contactapp.utils.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var listView: ListView
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var contactAdapter: ContactAdapter
    private var contactList = ArrayList<ContactModel>()
    private var selectedContactId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo các view
        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        listView = findViewById(R.id.listView)

        // Khởi tạo database helper
        dbHelper = DatabaseHelper(this)

        // Hiển thị danh sách ban đầu
        displayContacts()

        // Xử lý sự kiện khi chọn một item trong ListView
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = contactList[position]
            etName.setText(selectedContact.name)
            etPhone.setText(selectedContact.phone)
            selectedContactId = selectedContact.id
        }

        // Thiết lập các nút bấm
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnShow = findViewById<Button>(R.id.btnShow)

        btnAdd.setOnClickListener {
            addContact()
        }

        btnUpdate.setOnClickListener {
            updateContact()
        }

        btnDelete.setOnClickListener {
            deleteContact()
        }

        btnShow.setOnClickListener {
            displayContacts()
        }
    }

    private fun displayContacts() {
        contactList = dbHelper.getAllContacts()
        contactAdapter = ContactAdapter(this, contactList)
        listView.adapter = contactAdapter
    }

    private fun addContact() {
        val name = etName.text.toString()
        val phone = etPhone.text.toString()

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val result = dbHelper.addContact(name, phone)
        if (result > 0) {
            Toast.makeText(this, "Thêm liên hệ thành công", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Thêm liên hệ thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateContact() {
        if (selectedContactId == -1) {
            Toast.makeText(this, "Vui lòng chọn một liên hệ để sửa", Toast.LENGTH_SHORT).show()
            return
        }

        val name = etName.text.toString()
        val phone = etPhone.text.toString()

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val result = dbHelper.updateContact(selectedContactId, name, phone)
        if (result > 0) {
            Toast.makeText(this, "Cập nhật liên hệ thành công", Toast.LENGTH_SHORT).show()
            clearFields()
            displayContacts()
        } else {
            Toast.makeText(this, "Cập nhật liên hệ thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteContact() {
        if (selectedContactId == -1) {
            Toast.makeText(this, "Vui lòng chọn một liên hệ để xóa", Toast.LENGTH_SHORT).show()
            return
        }

        val result = dbHelper.deleteContact(selectedContactId)
        if (result > 0) {
            Toast.makeText(this, "Xóa liên hệ thành công", Toast.LENGTH_SHORT).show()
            clearFields()
            displayContacts()
        } else {
            Toast.makeText(this, "Xóa liên hệ thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        etName.text.clear()
        etPhone.text.clear()
        selectedContactId = -1
    }
}

