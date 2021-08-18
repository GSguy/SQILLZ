package com.example.sqillz.logic;

public enum DifficultyEnum {
        EASY(5),
        MEDIUM(10),
        HARD(20);

        private final int value;
        DifficultyEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
}
