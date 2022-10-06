package com.ccat.equipmentcalculator.model.entity;

import javax.persistence.*;
import java.util.HashMap;


@Entity
@Table(name="items_tmp")
public class Item {
    @Id
    private Long id;
    private String name;
    private int level;
    private int itemLevel;

    @Enumerated(EnumType.STRING)
    private ItemSlot itemSlot;

    @Transient
    private HashMap<String, Integer> statMap;
    //e.g. "Strength": 500

    //Constructors:
    public Item() {

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
