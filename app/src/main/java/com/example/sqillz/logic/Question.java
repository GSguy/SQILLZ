package com.example.sqillz.logic;

import android.util.Log;

import java.util.Random;

public class Question {
    private static int UUID = 0;
    private String question;
    private int questionID;
    private int answer;
    private int[] wrong_answers;
    private DifficultyEnum difficulty;


    public Question(String question, DifficultyEnum difficulty, int answer, int[] wrong_answers) {
        this.questionID = UUID;
        UUID = UUID + 1;
        this.question = question;
        this.difficulty = difficulty;
        this.answer = answer;
        this.wrong_answers = wrong_answers;
    }

    public int[] getPosAnswers() {
        int[] pa = new int[4];
        pa[0] = this.answer;
        pa[1] = this.wrong_answers[0];
        pa[2] = this.wrong_answers[1];
        pa[3] = this.wrong_answers[2];

        return pa;
    }

    public String getQuestion() {
        return this.question;
    }

    public static Question generatePlusMinusQuestion(int maximumRandomSize) {
        int num1, num2, realAnswer, fakeAnswer1, fakeAnswer2, fakeAnswer3;
        boolean isPlus;
        String question;

        Random rand = new Random();
        num1 = rand.nextInt(maximumRandomSize);
        num2 = rand.nextInt(maximumRandomSize);
        isPlus = rand.nextBoolean();

        if (num1 < num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        if (isPlus) {
            realAnswer = num1 + num2;
            question = String.format("%d + %d = ?", num1, num2);
        } else {
            realAnswer = num1 - num2;
            question = String.format("%d - %d = ?", num1, num2);
        }
        Log.i("plus", String.format("num1 = %d, num2 = %d", num1, num2));
        do{fakeAnswer1 = rand.nextInt(10) + realAnswer - 5;}while (fakeAnswer1 == realAnswer);
        do{fakeAnswer2 = rand.nextInt(10) + realAnswer - 5;}while ((fakeAnswer2 == realAnswer) || (fakeAnswer2 == fakeAnswer1));
        do{fakeAnswer3 = rand.nextInt(10) + realAnswer - 5;}while ((fakeAnswer3 == realAnswer) || (fakeAnswer3 == fakeAnswer1) || (fakeAnswer3 == fakeAnswer2));

        return new Question(question, null, realAnswer, new int[]{fakeAnswer1, fakeAnswer2, fakeAnswer3});
    }

    public static Question generateMultiDivisionQuestion(int maximumRandomSize, int maxMultiply) {
        int num1, num2, realAnswer, fakeAnswer1, fakeAnswer2, fakeAnswer3;
        boolean isMulti;
        String question;

        Random rand = new Random();
        num1 = (rand.nextInt(maximumRandomSize - 1)) + 1;
        isMulti = rand.nextBoolean();

        if (isMulti) {
            num2 = (rand.nextInt(maximumRandomSize - 1)) + 1;
            realAnswer = num1 * num2;
            question = String.format("%d * %d = ?", num1, num2);
        } else {
            int randomTemp = (rand.nextInt(maximumRandomSize - 1) + 1) % maxMultiply;
            num2 = randomTemp * num1;
            if (num2 > num1) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
            realAnswer = num1 / num2;
            question = String.format("%d : %d = ?", num1, num2);
        }
        Log.i("multi", String.format("num1 = %d, num2 = %d", num1, num2));
        do{fakeAnswer1 = rand.nextInt(10) + realAnswer - 5;}while (fakeAnswer1 == realAnswer);
        do{fakeAnswer2 = rand.nextInt(10) + realAnswer - 5;}while ((fakeAnswer2 == realAnswer) || (fakeAnswer2 == fakeAnswer1));
        do{fakeAnswer3 = rand.nextInt(10) + realAnswer - 5;}while ((fakeAnswer3 == realAnswer) || (fakeAnswer3 == fakeAnswer1) || (fakeAnswer3 == fakeAnswer2));

        return new Question(question, null, realAnswer, new int[]{fakeAnswer1, fakeAnswer2, fakeAnswer3});
    }

    public void setDifficulty(DifficultyEnum difficulty) {
        this.difficulty = difficulty;
    }
}
