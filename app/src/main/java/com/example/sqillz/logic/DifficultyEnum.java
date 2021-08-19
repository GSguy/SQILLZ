package com.example.sqillz.logic;

public enum DifficultyEnum {
    EASY(5),
    MEDIUM(8),
    HARD(15);

    private final int value; // point per question

    DifficultyEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
