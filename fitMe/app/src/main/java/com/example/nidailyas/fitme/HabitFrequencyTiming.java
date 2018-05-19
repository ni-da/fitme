package com.example.nidailyas.fitme;

import java.util.ArrayList;

public class HabitFrequencyTiming {
    //toDo: add days

    String habitFrequencyTimingId; //PK
    ArrayList<String> times = new ArrayList<String>();
    String habitId; //FK
    int habitPriority;

    public HabitFrequencyTiming() {
    }


    public HabitFrequencyTiming(String habitFrequencyTimingId,
                                ArrayList<String> times,
                                String habitId,
                                int habitPriority) {
        this.habitFrequencyTimingId = habitFrequencyTimingId;
        this.times = times;
        this.habitId = habitId;
        this.habitPriority = habitPriority;
    }

    public int getHabitPriority() {
        return habitPriority;
    }
    public String getHabitFrequencyTimingId() {
        return habitFrequencyTimingId;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public String getHabitId() {
        return habitId;
    }
}
