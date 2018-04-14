package com.example.nidailyas.fitme;

public class Planning {
    String planningId; // PK
    Habit[] habits; //habitFrequencyId's

    public Planning() {
    }

    public Planning(String planningId, Habit[] habits) {
        this.planningId = planningId;
        this.habits = habits;
    }

    public String getPlanningId() {
        return planningId;
    }

    public Habit[] getHabits() {
        return habits;
    }
}
