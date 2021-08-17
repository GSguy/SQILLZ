package com.example.sqillz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreEndTV, placeEndTV, highscoreEndTV;
    private Button rerunBTN, menuBTN;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        initView();

        loadStringsExtra();
        loadViewsData();
    }

    private void initView() {
        scoreEndTV = findViewById(R.id.scoreEndTV);
        placeEndTV = findViewById(R.id.placeEndTV);
        highscoreEndTV = findViewById(R.id.highscoreEndTV);

        rerunBTN = findViewById(R.id.rerunBTN);
        menuBTN = findViewById(R.id.menuBTN);
    }

    private void loadViewsData() {
        String scoretext = this.getResources().getString(R.string.score_text);
        scoreEndTV.setText(String.format("%s %d", scoretext, score));
    }

    private void loadStringsExtra() {
        String score;

        Intent intent = getIntent();
        score = intent.getStringExtra(ScoreActivity.this.getResources().getString(R.string.score_tag));
        this.score = Integer.parseInt(score);
    }
}