package com.example.firebasetestdata

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Firebase
        auth = Firebase.auth
        database = Firebase.database.reference

        // Xử lý đăng ký
        findViewById<Button>(R.id.btnSignUp).setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.let {
                            saveUserData(it.uid, email)
                            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Lỗi: ${task.exception?.message ?: "Unknown error"}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // Xử lý đăng nhập
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Lỗi: ${task.exception?.message ?: "Unknown error"}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // Xử lý hiển thị dữ liệu
        findViewById<Button>(R.id.btnShowData).setOnClickListener {
            database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        showAlert("Thông báo", "Không có dữ liệu người dùng")
                        return
                    }

                    val userList = StringBuilder()
                    for (userSnapshot in snapshot.children) {
                        val email = userSnapshot.child("email").value?.toString() ?: "N/A"
                        val timestamp = userSnapshot.child("createdAt").value as? Long ?: 0L

                        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                        val createdAt = dateFormat.format(Date(timestamp))

                        userList.append("Email: $email\nNgày tạo: $createdAt\n\n")
                    }

                    showAlert("Danh sách người dùng", userList.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@MainActivity,
                        "Lỗi đọc dữ liệu: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun saveUserData(userId: String, email: String) {
        val userData = hashMapOf(
            "email" to email,
            "createdAt" to ServerValue.TIMESTAMP
        )

        database.child("users").child(userId).setValue(userData)
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Lỗi lưu dữ liệu: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
