package com.example.sqillz.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private String user;
    private DifficultyEnum difficulty;
    private int score;
    private ArrayList<Question> easy_questions;
    private ArrayList<Question> med_questions;
    private ArrayList<Question> hard_questions;
    private ArrayList<Answer> answers;
    private Map<String, Integer> highscore;

    public Game(String user, DifficultyEnum difficulty){
        this.user = user;
        this.difficulty = difficulty;
        this.score = 0;
        this.easy_questions = new ArrayList<>();
        this.med_questions = new ArrayList<>();
        this.hard_questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        setQNA();
        loadHighscore();
    }

    private void loadHighscore(){
        // TODO: load highscore from file

        this.highscore = new HashMap<String, Integer>();
    }

    private void saveHighscore(){
        // TODO: save highscore to file
    }

    private void gameWon(){
        saveHighscore();
    }

    private void gameLost(){
        saveHighscore();
    }

    private void checkAnswer(Answer answer){

    }

    private void setQNA(){
        setQNA_Easy();
        setQNA_Medium();
        setQNA_Hard();
    }

    private void setQNA_Easy(){
        //Answer answer = new Answer();
        this.easy_questions.add(new Question("1+1=?", DifficultyEnum.EASY, 2));
        this.easy_questions.add(new Question("5-1=?", DifficultyEnum.EASY, 4));
        this.easy_questions.add(new Question("3+2=?", DifficultyEnum.EASY, 5));
        this.easy_questions.add(new Question("8-8=?", DifficultyEnum.EASY, 0));
        this.easy_questions.add(new Question("7+2=?", DifficultyEnum.EASY, 9));
        this.easy_questions.add(new Question("10+0=?", DifficultyEnum.EASY, 10));
        this.easy_questions.add(new Question("11-1=?", DifficultyEnum.EASY, 10));
        this.easy_questions.add(new Question("14+7=?", DifficultyEnum.EASY, 21));
        this.easy_questions.add(new Question("23-5=?", DifficultyEnum.EASY, 18));
        this.easy_questions.add(new Question("11-9=?", DifficultyEnum.EASY, 2));
    }

    private void setQNA_Medium(){
        this.med_questions.add(new Question("1*1=?", DifficultyEnum.MEDIUM, 1));
        this.med_questions.add(new Question("5*7=?", DifficultyEnum.MEDIUM, 35));
        this.med_questions.add(new Question("9*8=?", DifficultyEnum.MEDIUM, 72));
        this.med_questions.add(new Question("8:8=?", DifficultyEnum.MEDIUM, 1));
        this.med_questions.add(new Question("7*2=?", DifficultyEnum.MEDIUM, 14));
        this.med_questions.add(new Question("10*0=?", DifficultyEnum.MEDIUM, 0));
        this.med_questions.add(new Question("21:3=?", DifficultyEnum.MEDIUM, 7));
        this.med_questions.add(new Question("8*3=?", DifficultyEnum.MEDIUM, 24));
        this.med_questions.add(new Question("9*2=?", DifficultyEnum.MEDIUM, 18));
        this.med_questions.add(new Question("15*0=?", DifficultyEnum.MEDIUM, 0));
    }

    private void setQNA_Hard(){
        this.hard_questions.add(new Question("2x+1=5", DifficultyEnum.HARD, 2));
        this.hard_questions.add(new Question("(5-1)*x=12", DifficultyEnum.HARD, 3));
        this.hard_questions.add(new Question("3x-2=-20", DifficultyEnum.HARD, -6));
        this.hard_questions.add(new Question("2x-8=0", DifficultyEnum.HARD, 4));
        this.hard_questions.add(new Question("(10x+2)*2=4", DifficultyEnum.HARD, 0));
        this.hard_questions.add(new Question("3x=21", DifficultyEnum.HARD, 7));
        this.hard_questions.add(new Question("11-x=12", DifficultyEnum.HARD, -1));
        this.hard_questions.add(new Question("7x+7=7", DifficultyEnum.HARD, 0));
        this.hard_questions.add(new Question("4x-5=15", DifficultyEnum.HARD, 5));
        this.hard_questions.add(new Question("11x-x=10", DifficultyEnum.HARD, 1));
    }

    private boolean is_score_in_highscore(){
        return false;
    }
}
