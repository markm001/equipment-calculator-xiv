package com.ccat.equipmentcalculator.model;

import com.ccat.equipmentcalculator.model.Entity.EquipmentList;
import com.ccat.equipmentcalculator.model.Entity.Item;

import java.util.List;

public class GearSetResponse {
    private Long id;
    private int itemLevel;
    private List<Item> equippedItems;

    //Constructors:
    public GearSetResponse() {

    }
    public GearSetResponse(Long id, int itemLevel, List<Item> equippedItems) {
        this.id = id;
        this.itemLevel = itemLevel;
        this.equippedItems = equippedItems;
    }

    //Getters:
     public Long getId() {
        return id;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public List<Item> getEquippedItems() {
        return equippedItems;
    }
}
