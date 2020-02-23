package com.example.tier_calculator;

public class Problem {
    private String problem_id = "";
    private int level = 0;
    private int kudeki_level = 0;

    public Problem() {
    }

    public Problem(String pID, int lvl, int k_lvl) {
        this.problem_id = pID;
        this.level = lvl;
        this.kudeki_level = k_lvl;
    }

    protected String getProblemID() {
        return this.problem_id;
    }

    protected int getLevel() {
        return this.level;
    }

    protected int getKudekiLevel() {
        return this.kudeki_level;
    }
}
