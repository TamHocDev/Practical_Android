package com.example.twoactivitychallenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button buttonTextOne, buttonTextTwo, buttonTextThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonTextOne = findViewById(R.id.button_text_one);
        buttonTextTwo = findViewById(R.id.button_text_two);
        buttonTextThree = findViewById(R.id.button_text_three);


        buttonTextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTextDisplayActivity(1);
            }
        });

        // Thiết lập sự kiện click cho Button Two
        buttonTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTextDisplayActivity(2);
            }
        });

        // Thiết lập sự kiện click cho Button Three
        buttonTextThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTextDisplayActivity(3);
            }
        });
    }

    // Phương thức để khởi chạy TextDisplayActivity với text ID tương ứng
    private void launchTextDisplayActivity(int textId) {
        Intent intent = new Intent(MainActivity.this, TwoActivityChallengeDisplay.class);
        intent.putExtra("TEXT_ID", textId);
        startActivity(intent);
    }
}
