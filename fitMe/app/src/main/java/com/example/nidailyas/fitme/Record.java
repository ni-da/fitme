package com.example.nidailyas.fitme;

import java.sql.Time;
import java.util.Date;

public class Record {
    String recordId; // PK
    String result;
    Date examineDateTime; // dateTime
    //Time examineTime;
    String userId; // FK
    String habitId; // FK

    public Record(){}

    public Record(String recordId, String result, Date examineDateTime,
                  String userId, String habitId) {
        this.recordId = recordId;
        this.result = result;
        this.examineDateTime = examineDateTime;
        //this.examineTime = examineTime;
        this.userId = userId;
        this.habitId = habitId;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getResult() {
        return result;
    }

    public Date getExamineDateTime() {
        return examineDateTime;
    }

/*
    public Time getExamineTime() {
        return examineTime;
    }
    */

    public String getUserId() {
        return userId;
    }

    public String getHabitId() {
        return habitId;
    }
}
