package com.ccat.equipmentcalculator.model.Entity;

import java.util.HashMap;

public class Item {
    private Long id;
    private String name;
    private int level;
    //e.g. "Strength": 500
    private HashMap<String, Integer> statMap;

    //Constructors:
    public Item() {

    }
    public Item(Long id, String name, int level, HashMap<String, Integer> statMap) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.statMap = statMap;
    }

    //Getters:
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public HashMap<String, Integer> getStatMap() {
        return statMap;
    }
}
