package com.ccat.equipmentcalculator.model;

import com.ccat.equipmentcalculator.model.entity.CharacterClass;
import com.ccat.equipmentcalculator.model.entity.Item;

import java.util.List;

public class GearSetResponse {
    private Long id;

    private CharacterClass gearClass;

    private int itemLevel;
    private List<Item> equippedItems;

    //Constructors:
    public GearSetResponse() {

    }
    public GearSetResponse(Long id,CharacterClass gearClass, int itemLevel, List<Item> equippedItems) {
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

    public List<Item> getEquippedItems() {
        return equippedItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GearSetResponse that = (GearSetResponse) o;

        if (itemLevel != that.itemLevel) return false;
        if (!id.equals(that.id)) return false;
        if (gearClass != that.gearClass) return false;
        return equippedItems.equals(that.equippedItems);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + gearClass.hashCode();
        result = 31 * result + itemLevel;
        result = 31 * result + equippedItems.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GearSetResponse{" +
                "id=" + id +
                ", gearClass=" + gearClass +
                ", itemLevel=" + itemLevel +
                ", equippedItems=" + equippedItems +
                '}';
    }
}
