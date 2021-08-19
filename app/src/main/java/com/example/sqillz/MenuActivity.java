package com.example.sqillz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqillz.logic.DifficultyEnum;
import com.example.sqillz.logic.Game;
import com.example.sqillz.logic.Score;
import com.example.sqillz.logic.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private Button highestScoresBtn;
    private Button startGameBtn;
    private Button logOutBtn;

    private TextView helloTV;

    private RadioGroup difficultyRG;
    private RadioGroup speedRG;

    private FirebaseAuth mAuth;
    private HighScoresFragment highScoresFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        viewsInit();

        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHighestScoresFromFile();
    }

    private void setupViews() {
        highestScoresBtn.setOnClickListener(v -> openHighestScoresFragment());
        startGameBtn.setOnClickListener(v -> startGame());
        logOutBtn.setOnClickListener(v -> logout());

        setupUserName();
    }

    private void logout() {
        finish();
    }

    private DifficultyEnum getDifficultyLevelFromUser() {
        DifficultyEnum diff = DifficultyEnum.EASY;   // the default value
        switch (difficultyRG.getCheckedRadioButtonId()) {
            case R.id.easyRB:
                diff = DifficultyEnum.EASY;
                break;
            case R.id.mediumRB:
                diff = DifficultyEnum.MEDIUM;
                break;
            case R.id.hardRB:
                diff = DifficultyEnum.HARD;
                break;
        }
        return diff;
    }

    private void viewsInit() {
        this.highScoresFragment = new HighScoresFragment();

        highestScoresBtn = findViewById(R.id.highest_scores_btn);
        startGameBtn = findViewById(R.id.startGameBtn);
        logOutBtn = findViewById(R.id.logOutBtn);
        difficultyRG = findViewById(R.id.difficultyRG);
        speedRG = findViewById(R.id.speedRG);
        helloTV = findViewById(R.id.helloTV);
    }

    private void setupUserName() {
        mAuth = FirebaseAuth.getInstance();
        String name = mAuth.getCurrentUser().getEmail().split("@")[0];
        String helloText = this.getResources().getString(R.string.hello_user);
        helloTV.setText(String.format("%s %s", helloText, name));
    }

    private void startGame() {
        DifficultyEnum diff = getDifficultyLevelFromUser();
        String speed = getSpeedFromUser();
        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
        intent.putExtra(getResources().getString(R.string.diff_tag), "" + diff.name());
        intent.putExtra(getResources().getString(R.string.speed_tag), speed);
        startActivity(intent);
    }

    private String getSpeedFromUser() {
        String speed = getResources().getString(R.string.slow_text);
        switch (speedRG.getCheckedRadioButtonId()) {
            case R.id.slowRG:
                speed = getResources().getString(R.string.slow_text);
                break;
            case R.id.fastRG:
                speed = getResources().getString(R.string.fast_text);
                break;
        }
        return speed;
    }

    private void openHighestScoresFragment() {
        Log.d("fragment btn", "open fragment highest scores");
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity, highScoresFragment).commit();
        highestScoresBtn.setVisibility(View.INVISIBLE);
        startGameBtn.setVisibility(View.INVISIBLE);
        logOutBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (highScoresFragment == null || highScoresFragment.isRemoving())
            super.onBackPressed();
        else {
            getSupportFragmentManager().beginTransaction().remove(highScoresFragment).commit();
            highestScoresBtn.setVisibility(View.VISIBLE);
            startGameBtn.setVisibility(View.VISIBLE);
            logOutBtn.setVisibility(View.VISIBLE);
        }
    }

    public void loadHighestScoresFromFile() {
        Log.d("fragment", "loadHighestScoresFromFile");

        String filename = getResources().getString(R.string.Scores_Json_File);
        SharedPreferences sharedPref = getSharedPreferences(filename, Context.MODE_PRIVATE);

        // for reset all highest results: (!!!!!!!)
        // sharedPref.edit().clear().commit();

        String jsonFileString = sharedPref.getString(getString(R.string.Scores_Json_String),
                Utils.getJsonFromAssets(getApplicationContext(), "template_scores_json.json"));
        Log.d("jsonFileString", jsonFileString);
        Gson gson = new Gson();
        Type listScoreType = new TypeToken<ArrayList<Score>>() {
        }.getType();
        Game.highestScores = gson.fromJson(jsonFileString, listScoreType);
    }
}