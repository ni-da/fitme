package com.example.nidailyas.fitme;

import java.sql.Time;
import java.util.Date;

public class Record {
    String recordId; // PK
    String result;
    Date date; // dateTime
    String userId; // FK
    String habitId; // FK
}
