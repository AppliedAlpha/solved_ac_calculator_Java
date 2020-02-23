package com.example.tier_calculator;

public class Diff {
    private int expPerProblem = 256;
    private String name = "Unrated";

    public Diff() {
    }

    public Diff(String name, int expPerProblem) {
        this.expPerProblem = expPerProblem;
        this.name = name;
    }

    protected int getExpProblem() {
        return this.expPerProblem;
    }

    protected String getDiffName() {
        return this.name;
    }
}
