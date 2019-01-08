package com.wardziniak.kafka.app.basic;

public class FilterByList {

    private String actionHero;
    private String actionHero2;
    private String move;
    private int number;
    private String actionHero3;

    public FilterByList(String actionHero, String actionHero2, String move, int number, String actionHero3) {
        this.actionHero = actionHero;
        this.actionHero2 = actionHero2;
        this.move = move;
        this.number = number;
        this.actionHero3 = actionHero3;
    }

    public String getActionHero3() {
        return actionHero3;
    }
}
