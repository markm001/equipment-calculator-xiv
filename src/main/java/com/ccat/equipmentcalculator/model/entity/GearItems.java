package com.ccat.equipmentcalculator.model.entity;

import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;

import javax.persistence.Embeddable;

@Embeddable
public class GearItems {
    private Long id;
    private ItemSlot itemSlot;
    private Long itemId;


    //Constructors:
    public GearItems() {

    }
    public GearItems(Long id, ItemSlot itemSlot, Long itemId) {
        this.id = id;
        this.itemSlot = itemSlot;
        this.itemId = itemId;
    }

    //Getters:
    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public ItemSlot getItemSlot() {
        return itemSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GearItems gearItems = (GearItems) o;

        if (!id.equals(gearItems.id)) return false;
        if (itemSlot != gearItems.itemSlot) return false;
        return itemId.equals(gearItems.itemId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + itemSlot.hashCode();
        result = 31 * result + itemId.hashCode();
        return result;
    }
}
