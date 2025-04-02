package com.example.missedcallresponder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_CODE = 100
    private lateinit var messageEditText: EditText
    private lateinit var enableSwitch: Switch
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo các thành phần UI
        messageEditText = findViewById(R.id.messageEditText)
        enableSwitch = findViewById(R.id.enableSwitch)
        saveButton = findViewById(R.id.saveButton)

        // Tải tin nhắn đã lưu
        val sharedPrefs = getSharedPreferences("AutoMessagePrefs", MODE_PRIVATE)
        val savedMessage = sharedPrefs.getString("message", "Xin lỗi, tôi đang bận. Tôi sẽ gọi lại sau.")
        val isEnabled = sharedPrefs.getBoolean("enabled", false)

        messageEditText.setText(savedMessage)
        enableSwitch.isChecked = isEnabled

        // Xử lý sự kiện khi nhấn nút Lưu
        saveButton.setOnClickListener {
            val message = messageEditText.text.toString()
            val enabled = enableSwitch.isChecked

            // Lưu vào SharedPreferences
            val editor = sharedPrefs.edit()
            editor.putString("message", message)
            editor.putBoolean("enabled", enabled)
            editor.apply()

            Toast.makeText(this, "Đã lưu cài đặt", Toast.LENGTH_SHORT).show()
        }

        // Kiểm tra và yêu cầu quyền nếu cần
        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_CALL_LOG
        )

        val permissionsNeeded = ArrayList<String>()

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNeeded.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            var allGranted = true

            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false
                    break
                }
            }

            if (allGranted) {
                Toast.makeText(this, "Tất cả quyền đã được cấp", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Một số quyền bị từ chối. Ứng dụng có thể không hoạt động đúng.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}