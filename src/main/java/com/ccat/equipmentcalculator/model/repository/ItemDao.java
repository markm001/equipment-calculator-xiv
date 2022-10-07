package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemDao {
    private final List<Item> itemList = new ArrayList<>();

    public ItemDao() {
        HashMap<String, Integer> statMap= new HashMap<>();
        HashMap<String, Integer> dupeMap= new HashMap<>();
        statMap.put("Strength", 100);
        dupeMap.put("Strength", 100);
        dupeMap.put("Dexterity", 300);

        itemList.add(new Item(1L,
                "Chicken Tender",
                90,
                400,
                ItemSlot.PRIMARY,
                Set.of(ClassJobCategory.PLD),
                statMap));


        itemList.add(new Item(2L,
                "Chicken Shield",
                90,
                400,
                ItemSlot.SECONDARY,
                Set.of(ClassJobCategory.PLD),
                dupeMap));

        itemList.add(new Item(3L,
                "Multi-Equip Item",
                90,
                300,
                ItemSlot.PRIMARY,
                Set.of(ClassJobCategory.PLD, ClassJobCategory.WAR),
                statMap));
    }

    public Optional<Item> findById(Long itemId) {
        return itemList.stream().filter(i -> i.getId().equals(itemId)).findFirst();
    }

    public List<Item> findByIds(List<Long> itemIds) {
        return itemList.stream()
                .filter(i -> itemIds.contains(i.getId()))
                .collect(Collectors.toList());
    }

    public List<Item> findByCategoryAndLevel(ClassJobCategory category, int level) {
        return itemList.stream()
                .filter(i -> i.getJobCategories().contains(category) && i.getLevel() == level)
                .collect(Collectors.toList());
    }
}
