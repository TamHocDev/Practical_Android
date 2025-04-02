package com.example.timerapp

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewTimer: TextView
    private var seconds = 0
    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTimer = findViewById(R.id.textViewTimer)

        runnable = object : Runnable {
            override fun run() {
                seconds++
                textViewTimer.text = "$seconds giây"
                handler.postDelayed(this, 1000) // Cập nhật mỗi giây
            }
        }

        handler.post(runnable) // Bắt đầu đếm giờ
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Dừng đếm giờ khi activity bị hủy
    }
}