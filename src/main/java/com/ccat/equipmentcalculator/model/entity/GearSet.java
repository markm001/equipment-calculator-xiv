package com.ccat.equipmentcalculator.model.entity;

public class GearSet {
    private Long id;
    private CharacterClass gearClass;
    private int itemLevel;
    private EquipmentList equippedItems;

    //Constructors:
    public GearSet() {

    }

    public GearSet(Long id, CharacterClass gearClass, int itemLevel, EquipmentList equippedItems) {
        this.id = id;
        this.gearClass = gearClass;
        this.itemLevel = itemLevel;
        this.equippedItems = equippedItems;
    }

    //Getters:
    public Long getId() {
        return id;
    }

    public CharacterClass getGearClass() {
        return gearClass;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public EquipmentList getEquippedItems() {
        return equippedItems;
    }
}
