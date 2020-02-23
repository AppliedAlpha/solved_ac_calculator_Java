package com.example.tier_calculator;

public class Tier {
    private int level = 0;
    private String name = "Unrated";
    private long minEXP = 0L;
    private long maxEXP = 0L;

    public Tier() {
    }

    public Tier(int level, String name, long min, long max) {
        this.level = level;
        this.name = name;
        this.minEXP = min;
        this.maxEXP = max;
    }

    protected int getTierLevel() {
        return this.level;
    }

    protected String getTierName() {
        return this.name;
    }

    protected long getMinEXP() {
        return this.minEXP;
    }

    protected long getMaxEXP() {
        return this.maxEXP;
    }
}
