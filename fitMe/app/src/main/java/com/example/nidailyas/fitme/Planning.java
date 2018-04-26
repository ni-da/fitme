package com.example.nidailyas.fitme;

import java.util.ArrayList;

public class Planning {
    String planningId; // PK
    ArrayList<String> habitFrequencies = new ArrayList<String>();
    public Planning() {
    }

    public Planning(String planningId, ArrayList<String> habitFrequencies) {
        this.planningId = planningId;
        this.habitFrequencies = habitFrequencies;
    }

    public String getPlanningId() {
        return planningId;
    }

    public ArrayList<String> getHabitFrequencies() {
        return habitFrequencies;
    }
}
