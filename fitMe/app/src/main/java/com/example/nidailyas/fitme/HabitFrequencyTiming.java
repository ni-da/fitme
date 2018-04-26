package com.example.nidailyas.fitme;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class HabitFrequencyTiming {
    String habitFrequencyTiming; //PK
    ArrayList<String> times = new ArrayList<String>();
    String habitId; //FK

    public HabitFrequencyTiming() {
    }

    public HabitFrequencyTiming(String habitFrequencyTimeId,
                                ArrayList<String> times,
                                String habitId) {
        this.habitFrequencyTiming = habitFrequencyTimeId;
        this.times = times;
        this.habitId = habitId;
    }

    public String getHabitFrequencyTiming() {
        return habitFrequencyTiming;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public String getHabitId() {
        return habitId;
    }
}
