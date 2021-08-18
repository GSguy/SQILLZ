package com.example.sqillz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqillz.logic.DifficultyEnum;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreEndTV, placeEndTV, highscoreEndTV;
    private Button rerunBTN, menuBTN;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        initView();

        setupView();

        loadStringsExtra();
        loadViewsData();
    }

    private void setupView() {
        rerunBTN.setOnClickListener(v -> rerunClicked());
        menuBTN.setOnClickListener(v -> menuClicked());
    }

    private void menuClicked() {
        Intent i = new Intent(this, MenuActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void rerunClicked() {
        this.finish();
    }

    private void initView() {
        scoreEndTV = findViewById(R.id.scoreEndTV);
        placeEndTV = findViewById(R.id.placeEndTV);
        highscoreEndTV = findViewById(R.id.highscoreEndTV);

        rerunBTN = findViewById(R.id.rerunBTN);
        menuBTN = findViewById(R.id.menuBTN);
    }

    private void loadViewsData() {
        String scoretext = getResources().getString(R.string.score_text);
        scoreEndTV.setText(String.format("%s %d", scoretext, score));
    }

    private void loadStringsExtra() {
        String score;

        Intent intent = getIntent();
        score = intent.getStringExtra(getResources().getString(R.string.score_tag));
        this.score = Integer.parseInt(score);
    }
}