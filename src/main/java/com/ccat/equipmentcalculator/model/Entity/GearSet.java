package com.ccat.equipmentcalculator.model.Entity;

import java.util.HashMap;

public class GearSet {
    private Long id;
    private int characterLevel;
    private HashMap<ItemSlot,Item> equippedItem;

    //Constructors:
    public GearSet() {

    }

    public GearSet(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public GearSet(Long id, int characterLevel, HashMap<ItemSlot,Item> equippedItem) {
        this.id = id;
        this.characterLevel = characterLevel;
        this.equippedItem = equippedItem;
    }

    //Getters:
    public Long getId() {
        return id;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public HashMap<ItemSlot,Item> getEquippedItem() {
        return equippedItem;
    }
}
