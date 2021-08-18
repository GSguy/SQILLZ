package com.example.sqillz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import com.example.sqillz.logic.Question;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener  {

    // Game args
    private static int SLOW_MIN_DROP_SPEED = 3000;
    private static int SLOW_STARTING_SPEED = 7000;
    private static int FAST_MIN_DROP_SPEED = 2000;
    private static int FAST_STARTING_SPEED = 5500;

    private Game game;
    private DifficultyEnum difficulty;
    private TextView selectedTV;
    private String speed;
    private int dropDuration;

    // general
    private Handler handler = new Handler();
    private Vibrator vib;
    private Timer timer;
    private FirebaseAuth mAuth;
    private int width, hight;

    // View args
    private ImageView playerView;
    private TextView answer1TV, answer2TV, answer3TV, answer4TV, scoreTV, questionTV;
    private Button exitBTN, startBTN;

    // Position args
    private Animation animation;
    private int playerPosition;
    private float playerX;
    private List<Integer> laneOptions = new ArrayList<>();

    // flags
    private boolean start_flg = false;

    // swipe gesture args
    private float x1, x2;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        // init
        initViews();
        gestureDetector = new GestureDetector(GameActivity.this,this);

        // setup
        loadStringsExtra();
        setViews();
        setGame();
        setAnswersView();
    }

    private void loadStringsExtra() {
        String DifficultyLevel, speed;

        Intent intent = getIntent();
        DifficultyLevel = intent.getStringExtra(getResources().getString(R.string.diff_tag));
        speed = intent.getStringExtra(getResources().getString(R.string.speed_tag));
        this.difficulty = DifficultyEnum.valueOf(DifficultyLevel);
        this.speed = speed;
    }

    public boolean checkAnswer(){
        return isViewOverlapping(playerView, selectedTV);
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
        mAuth = FirebaseAuth.getInstance();
        String name = mAuth.getCurrentUser().getEmail().split("@")[0];
        game = new Game(name, difficulty);

        setScoreView();
        this.dropDuration = speed.equals(getResources().getString(R.string.slow_text))? SLOW_STARTING_SPEED : FAST_STARTING_SPEED;
    }

    private void setScoreView(){
        scoreTV.setText(String.format("%s %d",this.getResources().getString(R.string.score_text), game.getScore()));
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
        playerX = laneOptions.get(playerPosition);
        playerView.setX(playerX);
        playerView.setY((hight /10)*6);

        exitBTN.setOnClickListener(v -> exitButtonClicked());
        startBTN.setOnClickListener(v -> startButtonClicked());
    }

    private void startButtonClicked() {
        if (!start_flg) {
            this.start_flg = true;
            setNextQuestion();
            setAnimation();
        }
    }

    private void setNextQuestion() {
        setScoreView();
        Question question = game.getNextQuestion();
        questionTV.setText(String.format("%s",question.getQuestion()));

        setNextAnswers(question.getPosAnswers());
    }

    private void setNextAnswers(int[] answers){
        Random rnd = new Random();
        switch (rnd.nextInt(4)){
            case 0:
                answer1TV.setText(Integer.toString(answers[0]));
                answer2TV.setText(Integer.toString(answers[1]));
                answer3TV.setText(Integer.toString(answers[2]));
                answer4TV.setText(Integer.toString(answers[3]));
                selectedTV = answer1TV;
                break;
            case 1:
                answer1TV.setText(Integer.toString(answers[1]));
                answer2TV.setText(Integer.toString(answers[0]));
                answer3TV.setText(Integer.toString(answers[2]));
                answer4TV.setText(Integer.toString(answers[3]));
                selectedTV = answer2TV;
                break;
            case 2:
                answer1TV.setText(Integer.toString(answers[1]));
                answer2TV.setText(Integer.toString(answers[2]));
                answer3TV.setText(Integer.toString(answers[0]));
                answer4TV.setText(Integer.toString(answers[3]));
                selectedTV = answer3TV;
                break;
            case 3:
                answer1TV.setText(Integer.toString(answers[1]));
                answer2TV.setText(Integer.toString(answers[2]));
                answer3TV.setText(Integer.toString(answers[3]));
                answer4TV.setText(Integer.toString(answers[0]));
                selectedTV = answer4TV;
                break;
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

        questionTV = findViewById(R.id.questionTV);
        scoreTV = findViewById(R.id.scoreTV);

        exitBTN = findViewById(R.id.exitBTN);
        startBTN = findViewById(R.id.startBTN);
    }

    private void setAnimation(){
        // Set Animation
        animation = AnimationUtils.loadAnimation(GameActivity.this, R.anim.top_down);
        animation.setDuration(dropDuration);
        //animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(checkAnswer()) {
                    // Round Won
                    game.roundWon();
                    if (((dropDuration > SLOW_MIN_DROP_SPEED) && (speed.equals(getResources().getString(R.string.slow_text))))
                            || ((dropDuration > FAST_MIN_DROP_SPEED) && (speed.equals(getResources().getString(R.string.fast_text))))) {
                        dropDuration -= 400;
                        Log.i("speed", "" + dropDuration);
                    }
                    setNextQuestion();
                    setAnswersView();
                    setAnimation();
                }
                else {
                    // Game Over
                    Intent myIntent = new Intent(GameActivity.this, ScoreActivity.class);
                    myIntent.putExtra(GameActivity.this.getResources().getString(R.string.score_tag), ""+game.getScore());
                    startActivity(myIntent);
                }
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

    public void setAnswersView(){
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       gestureDetector.onTouchEvent(event);

       switch (event.getAction()){
           case MotionEvent.ACTION_DOWN:
               x1 = event.getX();
               break;
           case MotionEvent.ACTION_UP:
               x2 = event.getX();

               float valueX = x2 - x1;
               if(Math.abs(valueX) > 100){
                   if(x2>x1) {
                       // user swiped right on screen
                       changePos(false);
                   }
                   else {
                       // user swiped left on screen
                       changePos(true);
                   }
               }
               break;
       }
       return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void changePos(boolean swipedLeft) {
        //Move Car
        if (swipedLeft)
            playerPosition = (playerPosition - 1) % 4;
        else
            playerPosition = (playerPosition + 1) % 4;

        playerView.setX(laneOptions.get(playerPosition));
    }
}