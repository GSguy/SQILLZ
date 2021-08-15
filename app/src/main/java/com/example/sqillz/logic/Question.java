package com.example.sqillz.logic;

public class Question {
    private static int UUID = 0;
    private String question;
    private int questionID;
    private int answer;
    private DifficultyEnum difficulty;


    public Question(String question, DifficultyEnum difficulty, int answer){
        this.questionID = UUID;
        UUID = UUID + 1;
        this.question = question;
        this.difficulty = difficulty;
        this.answer = answer;
    }
}
