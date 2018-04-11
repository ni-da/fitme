package com.example.nidailyas.fitme;
import java.util.Date;
/**
 * Created by NidaI on 4/9/2018.
 */

public class User {
    String name;
    Date dateOfBirth;
    String gender;
    double weight;
    double height;
    double begin_bp_upper;
    double getBegin_bp_lower;

    public User() {
    }

    public User(String name, Date dateOfBirth, String gender, double weight, double height, double begin_bp_upper, double getBegin_bp_lower) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.begin_bp_upper = begin_bp_upper;
        this.getBegin_bp_lower = getBegin_bp_lower;
    }

    public double getBegin_bp_upper() {
        return begin_bp_upper;
    }

    public double getGetBegin_bp_lower() {
        return getBegin_bp_lower;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }
}