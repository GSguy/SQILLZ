package com.example.sqillz.logic;

public enum DifficultyEnum {
        EASY(2),
        MEDIUM(4),
        HARD(8);

        private final int value;
        DifficultyEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
}
