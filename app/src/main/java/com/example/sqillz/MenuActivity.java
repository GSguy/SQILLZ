package com.example.sqillz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private Button highestScoresBtn;
    private Button startGameBtn;
    private Button logOutBtn;

    private TextView helloUserName;

    private RadioGroup difficultyRadioGroup;
    private RadioGroup speedRadioGroup;

    private HighScoresFragment highScoresFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        this.highScoresFragment = new HighScoresFragment();

        highestScoresBtn = findViewById(R.id.highest_scores_btn);
        startGameBtn = findViewById(R.id.startGameBtn);
        logOutBtn = findViewById(R.id.logOutBtn);

        highestScoresBtn.setOnClickListener(v -> openHighestScoresFragment());

    }

    private void openHighestScoresFragment() {
        Log.d("fragment btn", "open fragment highest scores");
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_activity,highScoresFragment).commit();
        highestScoresBtn.setVisibility(View.INVISIBLE);
        startGameBtn.setVisibility(View.INVISIBLE);
        logOutBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (highScoresFragment == null || highScoresFragment.isRemoving())
            super.onBackPressed();
        else{
            getSupportFragmentManager().beginTransaction().remove(highScoresFragment).commit();
            highestScoresBtn.setVisibility(View.VISIBLE);
            startGameBtn.setVisibility(View.VISIBLE);
            logOutBtn.setVisibility(View.VISIBLE);
        }
    }
}