package com.example.nidailyas.fitme;

public class HabitFrequency {
    String habitFrequencyId; //PK
    int frequency;
    String habitId; //FK

    public HabitFrequency() {
    }

    public HabitFrequency(String habitFrequencyId, int frequency, String habitId) {
        this.habitFrequencyId = habitFrequencyId;
        this.frequency = frequency;
        this.habitId = habitId;
    }

    public String getHabitFrequencyId() {
        return habitFrequencyId;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getHabitId() {
        return habitId;
    }
}
