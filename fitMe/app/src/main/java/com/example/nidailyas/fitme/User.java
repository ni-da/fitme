package com.example.nidailyas.fitme;
import java.util.Date;
/**
 * Created by NidaI on 4/9/2018.
 */

public class User {
    public String name;
    public Date dateOfBirth;
    public char gender;
    public double weight;
    public double height;

    public User() {
    }

    public User(String name, Date dateOfBirth, char gender,
                double weight, double height) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
    }

    public User(String name) {
        this.name = name;

    }
}