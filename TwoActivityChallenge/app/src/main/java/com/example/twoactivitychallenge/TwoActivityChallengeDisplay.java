package com.example.twoactivitychallenge;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TwoActivityChallengeDisplay extends AppCompatActivity {
    private TextView textTitle;
    private TextView textContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_two_challenge_display);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ánh xạ các TextView từ layout
        textTitle = findViewById(R.id.text_title);
        textContent = findViewById(R.id.text_content);

        // Lấy textId từ Intent
        int textId = getIntent().getIntExtra("TEXT_ID", 1);

        // Hiển thị nội dung dựa trên textId
        displayText(textId);
    }
    private void displayText(int textId) {
        switch (textId) {
            case 1:
                textTitle.setText("Văn bản Một");
                textContent.setText(getString(R.string.text_passage_one));
                break;
            case 2:
                textTitle.setText("Văn bản Hai");
                textContent.setText(getString(R.string.text_passage_two));
                break;
            case 3:
                textTitle.setText("Văn bản Ba");
                textContent.setText(getString(R.string.text_passage_three));
                break;
            default:
                textTitle.setText("Văn bản Không xác định");
                textContent.setText("Không tìm thấy nội dung.");
        }
    }

}