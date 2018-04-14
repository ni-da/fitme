package com.example.nidailyas.fitme;

public class Habit {
    String habitId; // PK
    String habitName;
    String description;

    public Habit() {
    }


    public Habit(String habitId, String habitName, String description) {
        this.habitId = habitId;
        this.habitName = habitName;
        this.description = description;
    }

    public Habit(String habitId, String name, int frequency) {
    }

    public String getHabitId() {
        return habitId;
    }

    public String getHabitName() {
        return habitName;
    }

    public String getDescription() {
        return description;
    }
}
