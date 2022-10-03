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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GearSet gearSet = (GearSet) o;

        if (itemLevel != gearSet.itemLevel) return false;
        if (!id.equals(gearSet.id)) return false;
        if (gearClass != gearSet.gearClass) return false;
        return equippedItems.equals(gearSet.equippedItems);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + gearClass.hashCode();
        result = 31 * result + itemLevel;
        result = 31 * result + equippedItems.hashCode();
        return result;
    }
}
