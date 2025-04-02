package com.example.tcpclientapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var textViewResponse: TextView
    private lateinit var socket: Socket
    private lateinit var reader: BufferedReader
    private lateinit var writer: PrintWriter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)
        textViewResponse = findViewById(R.id.textViewResponse)

        Thread {
            try {
                socket = Socket("10.0.2.2", 12345) // Địa chỉ IP và cổng của Server
                reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                writer = PrintWriter(socket.getOutputStream(), true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            Thread {
                writer.println(message)
                val response = reader.readLine()
                Handler(Looper.getMainLooper()).post {
                    textViewResponse.text = response
                }
            }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}