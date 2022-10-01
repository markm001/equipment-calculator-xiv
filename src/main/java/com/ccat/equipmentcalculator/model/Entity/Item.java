package com.ccat.equipmentcalculator.model.Entity;

import java.util.HashMap;

public class Item {
    private Long id;
    private String name;
    private int level;
    private int itemLevel;
    private ItemSlot itemSlot;

    //e.g. "Strength": 500
    private HashMap<String, Integer> statMap;

    //Constructors:
    public Item() {

    }
    //TODO: REMOVE LATER - Empty-Item
    public Item(Long id, int itemLevel) {
        this.id = id;
        this.itemLevel = itemLevel;
    }

    public Item(Long id, String name, int level, int itemLevel, ItemSlot itemSlot, HashMap<String, Integer> statMap) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.itemLevel = itemLevel;
        this.itemSlot = itemSlot;
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

    public int getItemLevel() {
        return itemLevel;
    }

    public ItemSlot getItemSlot() {
        return itemSlot;
    }

    public HashMap<String, Integer> getStatMap() {
        return statMap;
    }
}
