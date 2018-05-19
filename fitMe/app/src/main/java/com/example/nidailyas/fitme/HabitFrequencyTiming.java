package com.example.nidailyas.fitme;

import java.util.ArrayList;

public class HabitFrequencyTiming {
    //toDO: add priority

    //toDo: add days

    String habitFrequencyTimingId; //PK
    ArrayList<String> times = new ArrayList<String>();
    String habitId; //FK

    public HabitFrequencyTiming() {
    }

    public HabitFrequencyTiming(String habitFrequencyTimeId,
                                ArrayList<String> times,
                                String habitId) {
        this.habitFrequencyTimingId = habitFrequencyTimeId;
        this.times = times;
        this.habitId = habitId;
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
