package com.example.contactapp.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contactapp.Model.ContactModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ContactsDB"
        private const val TABLE_CONTACTS = "contacts"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_CONTACTS ($KEY_ID INTEGER PRIMARY KEY, " +
                "$KEY_NAME TEXT, $KEY_PHONE TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    // Thêm liên hệ mới
    fun addContact(name: String, phone: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, name)
        values.put(KEY_PHONE, phone)

        // Chèn hàng mới
        val success = db.insert(TABLE_CONTACTS, null, values)
        db.close()
        return success
    }

    // Lấy tất cả liên hệ
    fun getAllContacts(): ArrayList<ContactModel> {
        val contactList = ArrayList<ContactModel>()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHONE))

                val contact = ContactModel(id, name, phone)
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contactList
    }

    // Cập nhật liên hệ
    fun updateContact(id: Int, name: String, phone: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, name)
        values.put(KEY_PHONE, phone)

        // Cập nhật hàng
        return db.update(TABLE_CONTACTS, values, "$KEY_ID = ?", arrayOf(id.toString()))
    }

    // Xóa liên hệ
    fun deleteContact(id: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_CONTACTS, "$KEY_ID = ?", arrayOf(id.toString()))
        db.close()
        return success
    }
}