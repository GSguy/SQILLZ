package com.example.sqillz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqillz.logic.Score;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreEndTV, placeEndTV, highScoreEndTV;
    private Button rerunBTN, menuBTN;
    private int userScore, userPlace, highestScore;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);

        initView();

        setupView();

        loadStringsExtra();
        loadViewsData();

        checkAndSaveScoreInHighestScoresList();
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
        highScoreEndTV = findViewById(R.id.highscoreEndTV);

        rerunBTN = findViewById(R.id.rerunBTN);
        menuBTN = findViewById(R.id.menuBTN);
    }

    private void loadViewsData() {
        String scoreText = getResources().getString(R.string.score_text);
        scoreEndTV.setText(String.format("%s %d", scoreText, userScore));

        String placeText = getResources().getString(R.string.place_text);
        placeEndTV.setText(String.format("%s %d", placeText, userPlace));

        String highestScoreText = getResources().getString(R.string.highest_score_text);
        highScoreEndTV.setText(String.format("%s %d", highestScoreText, highestScore));
    }

    private void loadStringsExtra() {
        String score, username;

        Intent intent = getIntent();

        score = intent.getStringExtra(getResources().getString(R.string.score_tag));
        username = intent.getStringExtra(getResources().getString(R.string.username_tag));

        this.username = username;
        this.userScore = Integer.parseInt(score);
    }

    public void checkAndSaveScoreInHighestScoresList()
    {
        Log.d("scoreActivity", "checkAndSaveScoreInHighestScoresList");

        Log.d("highestScores",HighScoresFragment.highestScores.toString());

        Score score = new Score(0,username, userScore);

        Log.d("newScore", score.toString());

        HighScoresFragment.checkIfIn10BestScoresAndSave(score);
        saveHighestScoresListsToJsonFile();
    }


    private void saveHighestScoresListsToJsonFile() {
        String filename = getResources().getString(R.string.Scores_Json_File);
        SharedPreferences sharedPref = getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        Type listScoreType = new TypeToken<ArrayList<Score>>() {}.getType();
        String scoresListJson = gson.toJson(HighScoresFragment.highestScores, listScoreType);

        editor.putString(getString(R.string.Scores_Json_String), scoresListJson);
        editor.apply();
    }

}