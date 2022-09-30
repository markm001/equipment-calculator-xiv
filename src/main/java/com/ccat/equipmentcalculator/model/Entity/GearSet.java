package com.ccat.equipmentcalculator.model.Entity;

import java.util.HashMap;

public class GearSet {
    private Long id;
    private int characterLevel;

    private EquipmentList equippedItems;

    //Constructors:
    public GearSet() {

    }

    public GearSet(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public GearSet(Long id, int characterLevel, EquipmentList equippedItems) {
        this.id = id;
        this.characterLevel = characterLevel;
        this.equippedItems = equippedItems;
    }

    //Getters:
    public Long getId() {
        return id;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public EquipmentList getEquippedItem() {
        return equippedItems;
    }
}
