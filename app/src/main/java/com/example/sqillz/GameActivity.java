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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    Vibrator vib;
    private Timer timer;

    // View args
    private ImageView playerView, Car2, Car3, answerView1,answerView2,answerView3,answerView4,Coin,life0,life1,life2;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        // init
        initViews();

        // setup
        setViews();
        setAnimation();
        setGame();
        setAnswers();
    }

    public boolean checkAnswer(){
        return isViewOverlapping(playerView, answerView1);
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
        score = 0;
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
    }

    private void initViews(){
        playerView = findViewById(R.id.imageView_player);
        answerView1 = findViewById(R.id.imageView_answer1);
        answerView2 = findViewById(R.id.imageView_answer2);
        answerView3 = findViewById(R.id.imageView_answer3);
        answerView4 = findViewById(R.id.imageView_answer4);
    }

    private void setAnimation(){
        // Set Animation
        animation = AnimationUtils.loadAnimation(GameActivity.this, R.anim.top_down);
        animation.setDuration(1000);
        animation.setFillAfter(true);
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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.cancel();
        answerView1.setAnimation(animation);
        answerView2.setAnimation(animation);
        answerView3.setAnimation(animation);
        answerView4.setAnimation(animation);
    }

    public void setAnswers(){
        answerView1.setX(laneOptions.get(0));
        answerView1.setY(0);
        answerView2.setX(laneOptions.get(1));
        answerView2.setY(0);
        answerView3.setX(laneOptions.get(2));
        answerView3.setY(0);
        answerView4.setX(laneOptions.get(3));
        answerView4.setY(0);

        visibilityFlag = false;
        swapVisibility();
    }

    public void swapVisibility(){
        if(visibilityFlag) {
            answerView1.setVisibility(View.INVISIBLE);
            answerView2.setVisibility(View.INVISIBLE);
            answerView3.setVisibility(View.INVISIBLE);
            answerView4.setVisibility(View.INVISIBLE);
            visibilityFlag = false;
        }
        else {
            answerView1.setVisibility(View.VISIBLE);
            answerView2.setVisibility(View.VISIBLE);
            answerView3.setVisibility(View.VISIBLE);
            answerView4.setVisibility(View.VISIBLE);
            visibilityFlag = true;
        }
    }
    public void changePos() {
        //Move Car
        if (player_move_right)
            playerPosition = (playerPosition + 1) % 4;
        else
            playerPosition = (playerPosition - 1) % 4;

    }
}