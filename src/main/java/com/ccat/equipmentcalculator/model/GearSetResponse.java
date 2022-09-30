package com.ccat.equipmentcalculator.model;

import com.ccat.equipmentcalculator.model.Entity.EquipmentList;
import com.ccat.equipmentcalculator.model.Entity.Item;

import java.util.List;

public class GearSetResponse {
    private Long id;
    private int characterLevel;
    private List<Item> equippedItems;

    //Constructors:
    public GearSetResponse() {

    }
    public GearSetResponse(Long id, int characterLevel, List<Item> equippedItems) {
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

    public List<Item> getEquippedItems() {
        return equippedItems;
    }
}
