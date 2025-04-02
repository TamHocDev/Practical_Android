package com.example.audiorecorder

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var audioRecorder: AudioRecorder
    private lateinit var buttonRecord: Button
    private lateinit var buttonPlay: Button
    private lateinit var listViewRecordings: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        audioRecorder = AudioRecorder(this)
        buttonRecord = findViewById(R.id.buttonRecord)
        buttonPlay = findViewById(R.id.buttonPlay)
        listViewRecordings = findViewById(R.id.listViewRecordings)

        buttonRecord.setOnClickListener {
            if (buttonRecord.text == "Ghi âm") {
                audioRecorder.startRecording()
                buttonRecord.text = "Dừng ghi"
            } else {
                audioRecorder.stopRecording()
                buttonRecord.text = "Ghi âm"
                updateRecordingsList()
            }
        }

        buttonPlay.setOnClickListener {
            if (listViewRecordings.selectedItem != null) {
                val filePath = listViewRecordings.selectedItem as String
                audioRecorder.playRecording(filePath)
            }
        }
        updateRecordingsList()
    }

    private fun updateRecordingsList() {
        val recordings = audioRecorder.getRecordings()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recordings)
        listViewRecordings.adapter = adapter
    }
}