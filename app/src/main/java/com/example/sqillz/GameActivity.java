package com.example.sqillz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqillz.logic.DifficultyEnum;
import com.example.sqillz.logic.Game;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameActivity extends AppCompatActivity {

    // Game args
    private Game game;

    // general
    private Handler handler = new Handler();
    Vibrator vib;
    private Timer timer;
    private FirebaseAuth mAuth;

    // View args
    private ImageView playerView, Car2, Car3,Coin,life0,life1,life2;
    private TextView answer1TV, answer2TV, answer3TV, answer4TV;
    private Button exitBTN, startBTN;
    private ImageButton Left,Right;

    // Position args
    private Animation animation;
    private float zombieY;
    private int playerPosition, zombieX, zombie2X, zombie3X, zombie4X,  coinX, zombieAndCoinSpeed;
    private float playerX;
    private boolean visibilityFlag;
    private long posPeriod = 20;
    private List<Integer> laneOptions = new ArrayList<>();

    // Speed args
    private String speed;

    private int lifeNum, score, distance = 0, hit_resize = 35, width, hight;

    // flags
    private boolean player_move_right;
    private boolean start_flg = false;
    private boolean pause_flg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        // init
        initViews();

        // setup
        setViews();
        setGame();
        setAnswers();
    }

    public boolean checkAnswer(){
        return isViewOverlapping(playerView, answer1TV);
    }

    private boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        int first_startX = firstPosition[0];
        int second_startX = secondPosition[0];

        int first_endX = firstView.getMeasuredWidth() + firstPosition[0];
        int second_endX = secondView.getMeasuredWidth() + secondPosition[0];
        //return r >= l && (r != 0 && l != 0);
        return first_startX >= second_startX && first_startX <= second_endX;
    }

    private void setGame(){
        this.start_flg = false;
        score = 0;
        mAuth = FirebaseAuth.getInstance();
        String name = mAuth.getCurrentUser().getEmail().split("@")[0];
        game = new Game(name, DifficultyEnum.EASY);
    }

    private void setViews(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        hight = size.y - findViewById(R.id.framelayout_answers).getLayoutParams().height;

        laneOptions.add(0);
        laneOptions.add(width/4);
        laneOptions.add((width/4)*2);
        laneOptions.add((width/4)*3);
        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        playerPosition = 2;
        playerX =  laneOptions.get(playerPosition);
        playerView.setX(playerX);
        playerView.setY((hight /10)*6);

        exitBTN.setOnClickListener(v -> exitButtonClicked());
        startBTN.setOnClickListener(v -> startButtonClicked());
    }

    private void startButtonClicked() {
        if (!start_flg) {
            Toast.makeText(GameActivity.this, "starting", Toast.LENGTH_SHORT).show();
            this.start_flg = true;
            setAnimation();
        }
    }

    private void exitButtonClicked() {
    }

    private void initViews(){
        playerView = findViewById(R.id.imageView_player);
        answer1TV = findViewById(R.id.answer1TV);
        answer2TV = findViewById(R.id.answer2TV);
        answer3TV = findViewById(R.id.answer3TV);
        answer4TV = findViewById(R.id.answer4TV);

        exitBTN = findViewById(R.id.exitBTN);
        startBTN = findViewById(R.id.startBTN);
    }

    private void setAnimation(){
        // Set Animation
        animation = AnimationUtils.loadAnimation(GameActivity.this, R.anim.top_down);
        animation.setDuration(1000);
        //animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(checkAnswer())
                    Toast.makeText(GameActivity.this,"touch",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(GameActivity.this,"miss",Toast.LENGTH_SHORT).show();

                animation.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        answer1TV.startAnimation(animation);
        answer2TV.startAnimation(animation);
        answer3TV.startAnimation(animation);
        answer4TV.startAnimation(animation);
    }

    public void setAnswers(){
        answer1TV.setX(laneOptions.get(0));
        answer1TV.setY(0);
        answer2TV.setX(laneOptions.get(1));
        answer2TV.setY(0);
        answer3TV.setX(laneOptions.get(2));
        answer3TV.setY(0);
        answer4TV.setX(laneOptions.get(3));
        answer4TV.setY(0);

        setVisibility();
    }

    public void setVisibility(){
        answer1TV.setVisibility(View.VISIBLE);
        answer2TV.setVisibility(View.VISIBLE);
        answer3TV.setVisibility(View.VISIBLE);
        answer4TV.setVisibility(View.VISIBLE);
    }
    public void changePos() {
        //Move Car
        if (player_move_right)
            playerPosition = (playerPosition + 1) % 4;
        else
            playerPosition = (playerPosition - 1) % 4;

    }
}