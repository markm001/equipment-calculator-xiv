package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.Entity.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemDao {
    private final List<Item> itemList = new ArrayList<>();

    public ItemDao() {
        HashMap<String, Integer> statMap= new HashMap<>();
        HashMap<String, Integer> dupeMap= new HashMap<>();
        statMap.put("Strength", 100);
        dupeMap.put("Strength", 100);
        dupeMap.put("Dexterity", 300);

        itemList.add(new Item(0L));

        itemList.add(new Item(1L,
                "Chicken Tender",
                90,
                statMap));


        itemList.add(new Item(2L,
                "Chicken Shield",
                90,
                dupeMap));
    }

    public Optional<Item> findById(Long itemId) {
        return itemList.stream().filter(i -> i.getId().equals(itemId)).findFirst();
    }

}
