package com.example.nidailyas.fitme;

import java.util.Date;

/**
 * Created by NidaI on 4/9/2018.
 */

public class User {
    String userId; //PK
    String name;
    Date dateOfBirth;
    String gender;
    double weight;
    double height;
    double begin_bp_upper;
    double begin_bp_lower;
    Long score;
    String planningId; //FK
    String levelId; //FK
    String profileImageUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId, String name, Date dateOfBirth, String gender,
                double weight, double height, double begin_bp_upper, double begin_bp_lower,
                Long score,
                String planningId, String levelId, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.begin_bp_upper = begin_bp_upper;
        this.begin_bp_lower = begin_bp_lower;
        this.score = score;
        this.planningId = planningId;
        this.levelId = levelId;
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getPlanningId() {
        return planningId;
    }

    public void setPlanningId(String planningId) {
        this.planningId = planningId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getName() {
        if (this.name != null) {
            return name;
        } else return "";
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDateOfBirth() {
//        String dateStr = String.format("%td/%tm/%tY", dateOfBirth, dateOfBirth, dateOfBirth);
//        return dateStr;
//    }

    public Date getDateOfBirth() {
        //String dateStr = String.format("%td/%tm/%tY", dateOfBirth, dateOfBirth, dateOfBirth);
        return this.dateOfBirth;
    }


//    public Date getDateOfBirthinDate() {
//        return dateOfBirth;
//    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        if (this.gender != null) {
            return gender;
        } else return "";

    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBegin_bp_upper() {
        return begin_bp_upper;
    }

    public void setBegin_bp_upper(double begin_bp_upper) {
        this.begin_bp_upper = begin_bp_upper;
    }

    public double getBegin_bp_lower() {
        return begin_bp_lower;
    }

    public void setBegin_bp_lower(double begin_bp_lower) {
        this.begin_bp_lower = begin_bp_lower;
    }
}