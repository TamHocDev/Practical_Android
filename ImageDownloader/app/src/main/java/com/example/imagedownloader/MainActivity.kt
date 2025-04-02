package com.example.imagedownloader

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUrl: EditText
    private lateinit var buttonDownload: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUrl = findViewById(R.id.editTextUrl)
        buttonDownload = findViewById(R.id.buttonDownload)
        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)

        buttonDownload.setOnClickListener {
            val imageUrl = editTextUrl.text.toString()
            if (imageUrl.isNotEmpty()) {
                ImageDownloader(imageView, progressBar).execute(imageUrl)
            }
        }
    }
}