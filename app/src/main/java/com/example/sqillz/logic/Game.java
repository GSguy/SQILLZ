package com.example.sqillz.logic;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private static int EASY_NUM_OF_QUESTIONS = 60;
    private static int EASY_MAX_VALUE = 15;

    private static int MID_NUM_OF_QUESTIONS = 40;
    private static int MID_MAX_PLUS_VALUE = 30;
    private static int MID_MAX_MUL_VALUE = 7;
    private static int MID_MAX_MUL_TIMES = 7;

    private static int HARD_NUM_OF_QUESTIONS = 25;
    private static int HARD_MAX_PLUS_VALUE = 75;
    private static int HARD_MAX_MUL_VALUE = 10;
    private static int HARD_MAX_MUL_TIMES = 10;

    private String user;
    private DifficultyEnum difficulty;
    private int score;
    private ArrayList<Question> easy_questions;
    private ArrayList<Question> mid_questions;
    private ArrayList<Question> hard_questions;
    private ArrayList<Integer> used_questions;
    private ArrayList<Answer> answers;
    private boolean isFast;

    public Game(String user, DifficultyEnum difficulty){
        this(user,difficulty, false);
    }

    public Game(String user, DifficultyEnum difficulty, boolean isFast){
        this.user = user;
        this.difficulty = difficulty;
        this.score = 0;
        this.easy_questions = new ArrayList<>();
        this.mid_questions = new ArrayList<>();
        this.hard_questions = new ArrayList<>();
        this.used_questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.isFast = isFast;
        setQNA();
    }

    private void setQNA(){
        switch (difficulty){
            case EASY:
                setQNA_Easy();
                break;
            case MEDIUM:
                setQNA_Medium();
                break;
            case HARD:
                setQNA_Hard();
                break;
        }
    }

    private void setQNA_Easy(){
        //Answer answer = new Answer();
//        this.easy_questions.add(new Question("1+1=?", DifficultyEnum.EASY, 2, new int[]{1,7,3}));
//        this.easy_questions.add(new Question("5-1=?", DifficultyEnum.EASY, 4, new int[]{1,6,5}));
//        this.easy_questions.add(new Question("3+2=?", DifficultyEnum.EASY, 5, new int[]{1,2,6}));
//        this.easy_questions.add(new Question("8-8=?", DifficultyEnum.EASY, 0, new int[]{16,10,1}));
//        this.easy_questions.add(new Question("7+2=?", DifficultyEnum.EASY, 9, new int[]{5,10,6}));
//        this.easy_questions.add(new Question("10+0=?", DifficultyEnum.EASY, 10, new int[]{20,0,18}));
//        this.easy_questions.add(new Question("11-1=?", DifficultyEnum.EASY, 10, new int[]{20,12,18}));
//        this.easy_questions.add(new Question("14+7=?", DifficultyEnum.EASY, 21, new int[]{20,10,11}));
//        this.easy_questions.add(new Question("23-5=?", DifficultyEnum.EASY, 18, new int[]{28,16,20}));
//        this.easy_questions.add(new Question("11-9=?", DifficultyEnum.EASY, 2, new int[]{11,9,20}));

        for (int i = 0; i < EASY_NUM_OF_QUESTIONS; i++) {
            Question q = Question.generatePlusMinusQuestion(EASY_MAX_VALUE);
            q.setDifficulty(DifficultyEnum.EASY);
            this.easy_questions.add(q);
        }
    }

    private void setQNA_Medium(){
//        this.med_questions.add(new Question("1*1=?", DifficultyEnum.MEDIUM, 1, new int[]{-1,-2,2}));
//        this.med_questions.add(new Question("5*7=?", DifficultyEnum.MEDIUM, 35, new int[]{25,40,13}));
//        this.med_questions.add(new Question("9*8=?", DifficultyEnum.MEDIUM, 72, new int[]{0,18,89}));
//        this.med_questions.add(new Question("8:8=?", DifficultyEnum.MEDIUM, 1, new int[]{-1,8,64}));
//        this.med_questions.add(new Question("7*2=?", DifficultyEnum.MEDIUM, 14, new int[]{10,9,3}));
//        this.med_questions.add(new Question("10*0=?", DifficultyEnum.MEDIUM, 0, new int[]{10,100,1}));
//        this.med_questions.add(new Question("21:3=?", DifficultyEnum.MEDIUM, 7, new int[]{21,18,0}));
//        this.med_questions.add(new Question("8*3=?", DifficultyEnum.MEDIUM, 24, new int[]{32,11,18}));
//        this.med_questions.add(new Question("9*2=?", DifficultyEnum.MEDIUM, 18, new int[]{24,9,15}));
//        this.med_questions.add(new Question("15*0=?", DifficultyEnum.MEDIUM, 0, new int[]{10,15,1}));

        for (int i = 0; i < MID_NUM_OF_QUESTIONS; i++) {
            Question q;
            boolean isPlus = new Random().nextBoolean();
            if (isPlus)
                q = Question.generatePlusMinusQuestion(MID_MAX_PLUS_VALUE);
            else
                q = Question.generateMultiDivisionQuestion(MID_MAX_MUL_VALUE,MID_MAX_MUL_TIMES);
            q.setDifficulty(DifficultyEnum.MEDIUM);
            this.mid_questions.add(q);
        }
    }

    private void setQNA_Hard(){
//        this.hard_questions.add(new Question("2x+1=5", DifficultyEnum.HARD, 2, new int[]{1,-2,3}));
//        this.hard_questions.add(new Question("(5-1)*x=12", DifficultyEnum.HARD, 3, new int[]{0,5,4}));
//        this.hard_questions.add(new Question("3x-2=-20", DifficultyEnum.HARD, -6, new int[]{10,6,8}));
//        this.hard_questions.add(new Question("2x-8=0", DifficultyEnum.HARD, 4, new int[]{0,-2,8}));
//        this.hard_questions.add(new Question("(10x+2)*2=4", DifficultyEnum.HARD, 0, new int[]{4,-2,10}));
//        this.hard_questions.add(new Question("3x=21", DifficultyEnum.HARD, 7, new int[]{0,-2,9}));
//        this.hard_questions.add(new Question("11-x=12", DifficultyEnum.HARD, -1, new int[]{1,-2,0}));
//        this.hard_questions.add(new Question("7x+7=7", DifficultyEnum.HARD, 0, new int[]{-7,-2,1}));
//        this.hard_questions.add(new Question("4x-5=15", DifficultyEnum.HARD, 5, new int[]{20,4,-2}));
//        this.hard_questions.add(new Question("11x-x=10", DifficultyEnum.HARD, 1, new int[]{10,11,-1}));

        for (int i = 0; i < HARD_NUM_OF_QUESTIONS; i++) {
            Question q;
            boolean isPlus = new Random().nextBoolean();
            if (isPlus)
                q = Question.generatePlusMinusQuestion(HARD_MAX_PLUS_VALUE);
            else
                q = Question.generateMultiDivisionQuestion(HARD_MAX_MUL_VALUE,HARD_MAX_MUL_TIMES);
            q.setDifficulty(DifficultyEnum.HARD);
            this.hard_questions.add(q);
        }
    }

    private boolean is_score_in_highscore(){
        return false;
    }

    public Question getNextQuestion() {
        ArrayList<Question> current = getCurrentDifficultyList();
        if (used_questions.size() >= current.size()){
            used_questions = new ArrayList<>();
        }
        int index;
        do {
            Random rnd = new Random();
            index = rnd.nextInt(current.size());
        }while (used_questions.contains(index));
        used_questions.add(index);
        return current.get(index);
    }

    private ArrayList<Question> getCurrentDifficultyList() {
        switch (difficulty){
            case EASY:
                return easy_questions;
            case MEDIUM:
                return mid_questions;
            case HARD:
                return hard_questions;
            default:
                return null;
        }
    }

    public int getScore() {
        return this.score;
    }

    public String getUser() {
        return this.user;
    }

    public void roundWon() {
        this.score += isFast ? this.difficulty.getValue() : this.difficulty.getValue() * 1.5;
    }
}
