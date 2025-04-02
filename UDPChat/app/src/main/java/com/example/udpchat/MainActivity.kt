package com.example.udpchat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class MainActivity : AppCompatActivity() {

    private lateinit var editTextIpAddress: EditText
    private lateinit var editTextPort: EditText
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var textViewMessages: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val receivePort = 12346 // Cổng nhận tin nhắn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextIpAddress = findViewById(R.id.editTextIpAddress)
        editTextPort = findViewById(R.id.editTextPort)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)
        textViewMessages = findViewById(R.id.textViewMessages)

        buttonSend.setOnClickListener {
            sendMessage()
        }

        Thread {
            receiveMessages()
        }.start()
    }

    private fun sendMessage() {
        Thread {
            try {
                val ipAddress = InetAddress.getByName(editTextIpAddress.text.toString())
                val port = editTextPort.text.toString().toInt()
                val message = editTextMessage.text.toString().toByteArray()
                val packet = DatagramPacket(message, message.size, ipAddress, port)
                val socket = DatagramSocket()
                socket.send(packet)
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun receiveMessages() {
        try {
            val socket = DatagramSocket(receivePort)
            val buffer = ByteArray(1024)
            while (true) {
                val packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)
                val message = String(packet.data, 0, packet.length)
                handler.post {
                    textViewMessages.append("$message\n")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Đóng socket (nếu cần)
    }
}