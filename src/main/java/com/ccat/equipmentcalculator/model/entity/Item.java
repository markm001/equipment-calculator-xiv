package com.ccat.equipmentcalculator.model.entity;

import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


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

    @ElementCollection(targetClass = ClassJobCategory.class)
    @Enumerated(EnumType.STRING)
    private Set<ClassJobCategory> jobCategories;

    @ElementCollection
    @CollectionTable(name="STAT_VALUE_MAPPING",
        joinColumns = {@JoinColumn(name="item_id", referencedColumnName="id")})
    @MapKeyColumn(name="stat_name")
    @Column(name="stat_value")
    private Map<String, Integer> statMap;
    //e.g. "Strength": 500

    //Constructors:
    public Item() {

    }

    public Item(Long id, String name, int level, int itemLevel, ItemSlot itemSlot, Set<ClassJobCategory> jobCategories, HashMap<String, Integer> statMap) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.itemLevel = itemLevel;
        this.itemSlot = itemSlot;
        this.jobCategories = jobCategories;
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

    public Set<ClassJobCategory> getJobCategories() {
        return jobCategories;
    }

    public Map<String, Integer> getStatMap() {
        return statMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (level != item.level) return false;
        if (itemLevel != item.itemLevel) return false;
        if (!id.equals(item.id)) return false;
        if (!name.equals(item.name)) return false;
        if (itemSlot != item.itemSlot) return false;
        if (!jobCategories.equals(item.jobCategories)) return false;
        return statMap.equals(item.statMap);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + level;
        result = 31 * result + itemLevel;
        result = 31 * result + itemSlot.hashCode();
        result = 31 * result + jobCategories.hashCode();
        result = 31 * result + statMap.hashCode();
        return result;
    }
}
