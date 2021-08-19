package com.example.sqillz.logic;

public class Score {
    private int place;
    private String name;
    private int score;

    public Score(int place, String name, int score){
        this.place = place;
        this.name = name;
        this.score = score;
    }

    private Integer checkIfTheScoreIsZero(int score) {
        // if longTime is 0 , it means there is no new result.
        if (score == 0)
            return Integer.MAX_VALUE; // save max value for the algorithm of "save the minimum result"
        else
            return score;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName(){ return this.name; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Score{" +
                "place='" + place + '\'' +
                ", name=" + name +
                ", score=" + score +
                '}';
    }
}