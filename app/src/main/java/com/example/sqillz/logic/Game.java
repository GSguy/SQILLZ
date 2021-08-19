package com.example.sqillz.logic;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final static int EASY_NUM_OF_QUESTIONS = 60;
    private final static int EASY_MAX_VALUE = 15;

    private final static int MID_NUM_OF_QUESTIONS = 40;
    private final static int MID_MAX_PLUS_VALUE = 30;
    private final static int MID_MAX_MUL_VALUE = 7;
    private final static int MID_MAX_MUL_TIMES = 7;

    private final static int HARD_NUM_OF_QUESTIONS = 25;
    private final static int HARD_MAX_PLUS_VALUE = 75;
    private final static int HARD_MAX_MUL_VALUE = 10;
    private final static int HARD_MAX_MUL_TIMES = 10;

    private ArrayList<Question> easy_questions;
    private ArrayList<Question> mid_questions;
    private ArrayList<Question> hard_questions;
    private ArrayList<Integer> used_questions;
    private DifficultyEnum difficulty;
    private boolean isFast;
    private int score;
    public static ArrayList<Score> highestScores = new ArrayList<>();
    public static int highestScoreInteger;

    public Game(DifficultyEnum difficulty) {
        this(difficulty, false);
    }

    public Game(DifficultyEnum difficulty, boolean isFast) {
        this.difficulty = difficulty;
        this.score = 0;
        this.easy_questions = new ArrayList<>();
        this.mid_questions = new ArrayList<>();
        this.hard_questions = new ArrayList<>();
        this.used_questions = new ArrayList<>();
        this.isFast = isFast;
        setQNA();
    }

    private void setQNA() {
        switch (difficulty) {
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

    private void setQNA_Easy() {
        for (int i = 0; i < EASY_NUM_OF_QUESTIONS; i++) {
            Question q = Question.generatePlusMinusQuestion(EASY_MAX_VALUE);
            this.easy_questions.add(q);
        }
    }

    private void setQNA_Medium() {
        for (int i = 0; i < MID_NUM_OF_QUESTIONS; i++) {
            Question q;
            boolean isPlus = new Random().nextBoolean();
            if (isPlus)
                q = Question.generatePlusMinusQuestion(MID_MAX_PLUS_VALUE);
            else
                q = Question.generateMultiDivisionQuestion(MID_MAX_MUL_VALUE, MID_MAX_MUL_TIMES);
            this.mid_questions.add(q);
        }
    }

    private void setQNA_Hard() {
        for (int i = 0; i < HARD_NUM_OF_QUESTIONS; i++) {
            Question q;
            boolean isPlus = new Random().nextBoolean();
            if (isPlus)
                q = Question.generatePlusMinusQuestion(HARD_MAX_PLUS_VALUE);
            else
                q = Question.generateMultiDivisionQuestion(HARD_MAX_MUL_VALUE, HARD_MAX_MUL_TIMES);
            this.hard_questions.add(q);
        }
    }

    public Question getNextQuestion() {
        ArrayList<Question> current = getCurrentDifficultyList();
        if (used_questions.size() >= current.size()) {
            used_questions = new ArrayList<>();
        }
        int index;
        do {
            Random rnd = new Random();
            index = rnd.nextInt(current.size());
        } while (used_questions.contains(index));
        used_questions.add(index);
        return current.get(index);
    }

    private ArrayList<Question> getCurrentDifficultyList() {
        switch (difficulty) {
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

    public void roundWon() {
        this.score += isFast ? this.difficulty.getValue() : this.difficulty.getValue() * 1.5;
    }
}
