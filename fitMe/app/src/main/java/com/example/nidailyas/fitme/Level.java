package com.example.nidailyas.fitme;

public class Level {
    String levelId; // PK
    String signatureName;
    int minScore;
    int maxScore;

    public Level(){}
    public Level(String levelId, String signatureName, int minScore, int maxScore) {
        this.levelId = levelId;
        this.signatureName = signatureName;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public String getLevelId() {
        return levelId;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
