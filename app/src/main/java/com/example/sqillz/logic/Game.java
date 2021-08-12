package com.example.sqillz.logic;

import java.util.Map;

public class Game {
    private String user;
    private DifficultyEnum difficulty;
    private int score;
    private Question[] questions;
    private Answer[] answers;
    private Map<String, Integer> highscore;

    public Game(String user, DifficultyEnum difficulty){
        this.user = user;
        this.difficulty = difficulty;
    }

    private void loadHighscore(){

    }

    private void saveHighscore(){

    }

    private void gameWon(){

    }

    private void gameLost(){

    }

    private void checkAnswer(Answer answer){

    }

    private boolean is_score_in_highscore(){
        return false;
    }
}
