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

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public char getGender() {
        return gender;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}