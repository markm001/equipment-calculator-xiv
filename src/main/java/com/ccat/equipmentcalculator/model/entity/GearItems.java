package com.ccat.equipmentcalculator.model.entity;

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
}
