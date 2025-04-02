package com.example.oursharedpreference

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oursharedpreference.ui.theme.OurSharedPreferenceTheme

class MainActivity : ComponentActivity() {
    //khai bao thuoc tinh
    private lateinit var edt_phone: EditText
    private lateinit var btn_save: Button
    private lateinit var btn_load: Button
    private lateinit var txt_info: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tham chieu cac phan tu
        edt_phone = findViewById(R.id.edtPhone)
        btn_save = findViewById(R.id.btnSave)
        btn_load = findViewById(R.id.btnLoad)
        txt_info = findViewById(R.id.txtInfo)

        //xu ly su kien
        btn_save.setOnClickListener {
            //lay text tu edt_phone
            val phone = edt_phone.text.toString()
            //luu phone vao SharedPreferences
            val sharedPreferences = getSharedPreferences("phone", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("phone", phone)
            editor.apply()
        }

        btn_load.setOnClickListener {
            //doc phone tu SharedPreferences
            val sharedPreferences = getSharedPreferences("phone", MODE_PRIVATE)
            val phone = sharedPreferences.getString("phone", "")
            txt_info.text = phone
        }
    }
}
