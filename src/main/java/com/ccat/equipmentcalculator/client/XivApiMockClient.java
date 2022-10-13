package com.ccat.equipmentcalculator.client;

import com.ccat.equipmentcalculator.exception.InvalidIdException;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Profile("test")
@Service
public class XivApiMockClient implements XivApiClient {
    List<Item> itemList = new ArrayList<>();
    List<String> stats = List.of(
            "Strength",
            "Dexterity",
            "Vitaliy",
            "Intelligence",
            "Mind",
            "Critical Hit",
            "Determination",
            "Direct Hit Rate",
            "Defense",
            "Magic Defense",
            "Attack Power",
            "Skill Speed",
            "Attack Magic Potency",
            "Healing Magic Potency",
            "Spell Speed",
            "Tenacity",
            "Piety"
    );

    public XivApiMockClient() {
        for(int i=0; i<50; i++){
            itemList.add(generateRandomItem());
        }
    }

    public void saveItems(List<Item> itemRequest) {
        itemList.addAll(itemRequest);
    }

    @Override
    public List<Item> getItemsByCategoryAndLevel(ClassJobCategory category, int level) {
        return itemList.stream()
                .filter(i -> (i.getJobCategories().contains(category))&&(i.getLevel()==level))
                .collect(Collectors.toList());
    }

    @Override
    public Item getItemById(Long id) {
        return itemList.stream().filter(i -> i.getId().equals(id)).findFirst()
                .orElseThrow(
                        new InvalidIdException(String.format("Item with Id:%d not found.", id),
                                HttpStatus.BAD_REQUEST));
    }

    public List<Item> getAllItems() {
        return itemList;
    }

    private Item generateRandomItem() {
        Random rand = new Random();
        int randomLevel = rand.nextInt(90) + 1;
        int randomItemLevel = rand.nextInt(400) + 1;
        int randomSlotId = rand.nextInt(ItemSlot.values().length);
        ItemSlot randomSlot = ItemSlot.values()[randomSlotId];

        Set<ClassJobCategory> jobCategories = new HashSet<>();
        for(int i=0; i<(rand.nextInt(2) + 1); i++) {
            int randomJobId = rand.nextInt(ClassJobCategory.values().length);
            jobCategories.add(ClassJobCategory.values()[randomJobId]);
        }

        Map<String, Integer> statMap = new HashMap<>();
        for(int i=0; i<(rand.nextInt(4) + 1); i++) {
            int y = rand.nextInt(stats.size());
            statMap.put(stats.get(y),rand.nextInt(300) + 1 );
        }

        return new Item(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                UUID.randomUUID().toString(),
                randomLevel,
                randomItemLevel,
                randomSlot,
                jobCategories,
                new HashMap<>(statMap));
    }
}
