package com.example.sqillz.logic;

public class Question {
    private static int UUID = 0;
    private String question;
    private int questionID;
    private int answer;
    private int[] wrong_answers;
    private DifficultyEnum difficulty;


    public Question(String question, DifficultyEnum difficulty, int answer, int[] wrong_answers){
        this.questionID = UUID;
        UUID = UUID + 1;
        this.question = question;
        this.difficulty = difficulty;
        this.answer = answer;
        this.wrong_answers = wrong_answers;
    }

    public int[] getPosAnswers(){
        int[] pa = new int[4];
        pa[0] = this.answer;
        pa[1] = this.wrong_answers[0];
        pa[2] = this.wrong_answers[1];
        pa[3] = this.wrong_answers[2];

        return pa;
    }
}
