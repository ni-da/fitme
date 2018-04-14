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

    public String getUserId() {
        return userId;
    }

    public Long getScore() {
        return score;
    }

    public String getPlanningId() {
        return planningId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setBegin_bp_upper(double begin_bp_upper) {
        this.begin_bp_upper = begin_bp_upper;
    }

    public void setBegin_bp_lower(double begin_bp_lower) {
        this.begin_bp_lower = begin_bp_lower;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public void setPlanningId(String planningId) {
        this.planningId = planningId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        String dateStr = String.format("%td/%tm/%tY", dateOfBirth, dateOfBirth, dateOfBirth);
        return dateStr;
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

    public double getBegin_bp_upper() {
        return begin_bp_upper;
    }

    public double getBegin_bp_lower() {
        return begin_bp_lower;
    }
}