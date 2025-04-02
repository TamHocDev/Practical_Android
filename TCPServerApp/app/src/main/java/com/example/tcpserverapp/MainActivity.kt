package com.example.tcpserverapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var textViewLog: TextView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewLog = findViewById(R.id.textViewLog)

        Thread {
            startServer()
        }.start()
    }

    private fun startServer() {
        try {
            val serverSocket = ServerSocket(12345)
            handler.post { textViewLog.append("Server đang chạy...\n") }

            while (true) {
                val clientSocket = serverSocket.accept()
                val clientAddress = clientSocket.inetAddress.hostAddress
                handler.post { textViewLog.append("Client kết nối: $clientAddress\n") }

                Thread {
                    handleClient(clientSocket)
                }.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleClient(clientSocket: Socket) {
        try {
            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val writer = PrintWriter(clientSocket.getOutputStream(), true)

            while (true) {
                val message = reader.readLine() ?: break
                handler.post { textViewLog.append("Nhận từ client: $message\n") }
                writer.println("Server nhận được: $message")
            }

            clientSocket.close()
            val clientAddress = clientSocket.inetAddress.hostAddress
            handler.post { textViewLog.append("Client ngắt kết nối: $clientAddress\n") }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}