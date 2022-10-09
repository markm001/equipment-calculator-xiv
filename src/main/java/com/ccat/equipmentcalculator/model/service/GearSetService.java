package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.exception.InvalidIdException;
import com.ccat.equipmentcalculator.exception.InvalidItemSlotException;
import com.ccat.equipmentcalculator.exception.InvalidJobClassException;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.entity.GearItems;
import com.ccat.equipmentcalculator.model.entity.GearSet;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.CharacterClass;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.floor;

@Service
public class GearSetService {
    private final GearSetDao gearSetDao;
    private final ItemService itemService;

    public GearSetService(GearSetDao gearSetDao, ItemService itemService) {
        this.gearSetDao = gearSetDao;
        this.itemService = itemService;
    }

    public GearSet createEmptyGearSet(GearSet gearSetRequest) {

        GearSet savedGearSet = new GearSet(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                gearSetRequest.getProfileId(),
                gearSetRequest.getGearClass(),
                List.of());

        gearSetDao.save(savedGearSet);

        return savedGearSet;
    }

    public GearSetResponse getGearSetById(Long setId) {

        GearSet retrievedSet = retrieveGearSetById(setId);

        List<Long> itemIds = retrievedSet.getEquippedItems().stream()
                .map(GearItems::getItemId)
                .collect(Collectors.toList());

        List<Item> retrievedItems = itemService.getItemsByIds(itemIds);

        Map<ItemSlot, Item> itemSlotMap = new HashMap<>();
        for(ItemSlot slot : ItemSlot.values()) {
            Item itemForSlot = retrievedItems.stream()
                    .filter(rI -> rI.getItemSlot().equals(slot))
                    .findFirst()
                    .orElse(new Item());
            itemSlotMap.put(slot, itemForSlot);
        }

        List<Item> slotItems = new ArrayList<>(itemSlotMap.values());
        int averageItemLevel = calculateAverageItemLevels(slotItems);

        return new GearSetResponse(
                retrievedSet.getId(),
                retrievedSet.getGearClass(),
                averageItemLevel,
                retrievedItems
        );
    }

    public GearSetResponse updateGearSetEquipment(Long gearSetId, List<GearItems> gearItemsRequest) {
        // check GearSet validity:
        GearSet retrievedSet = retrieveGearSetById(gearSetId);
        // get currently equipped GearSet Items:
        List<Long> oldItemIdsRequest = retrievedSet.getEquippedItems().stream()
                .map(GearItems::getItemId)
                .collect(Collectors.toList());
        List<Item> oldItemList = itemService.getItemsByIds(oldItemIdsRequest);

        // get requested Items via Ids:
        List<Long> itemIdsRequest = gearItemsRequest.stream()
                .map(GearItems::getItemId)
                .collect(Collectors.toList());
        List<Item> newItemList = itemService.getItemsByIds(itemIdsRequest);

        // compare request slots to actual item-slot & job-class and actual item-class:
        gearItemsRequest
                .forEach(gI -> {
                    Item item = newItemList.stream()
                            .filter(i -> i.getId().equals(gI.getItemId())).findFirst().get();
                    if(!item.getItemSlot().equals(gI.getItemSlot())) {
                        throw new InvalidItemSlotException(
                                String.format("Item with Id:%d and Slot:%s cannot be equipped into Slot:%s",item.getId(),item.getItemSlot(),gI.getItemSlot()),
                                HttpStatus.BAD_REQUEST);
                    }
                    if(!item.getJobCategories().contains(retrievedSet.getGearClass().getAbbreviation())) {
                        throw new InvalidJobClassException(
                                String.format("Item with Id:%d and Job-Class:%s cannot be equipped to Job-Class:%s",
                                        item.getId(), item.getJobCategories(),retrievedSet.getGearClass().getAbbreviation()),
                                HttpStatus.BAD_REQUEST);
                    }
                });

        // all newItem-Slots:
        List<ItemSlot> slotsToWrite = gearItemsRequest.stream()
                .map(GearItems::getItemSlot)
                .collect(Collectors.toList());
        // check if oldItem-slot conflicts with newItem-slots
        List<Item> cleanItemList = oldItemList.stream()
                .filter(i -> !(slotsToWrite.contains(i.getItemSlot())))
                .collect(Collectors.toList());

        // join CleanedItems + NewItems
        List<Item> itemListRequest = Stream.concat(cleanItemList.stream(), newItemList.stream())
                .collect(Collectors.toList());

        // compile Slot-Map - Item or empty Item
        Map<ItemSlot, Item> itemSlotMap = new HashMap<>();
        for(ItemSlot slot : ItemSlot.values()) {
            Item item = itemListRequest.stream()
                    .filter(i -> i.getItemSlot().equals(slot))
                    .findFirst()
                    .orElse(new Item());
            itemSlotMap.put(slot, item);
        }

        // Calculate average Item-Level for all Items:
        List<Item> allSlotItems = new ArrayList<>(itemSlotMap.values());
        int averageItemLevel = calculateAverageItemLevels(allSlotItems);

        // compile GearSet for Items:
        List<GearItems> gearItemsRequestList = itemListRequest.stream()
                .map(i -> new GearItems(
                        UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                        i.getItemSlot(),
                        i.getId()))
                .collect(Collectors.toList());

        // save new GearSet:
        gearSetDao.save(new GearSet(
                retrievedSet.getId(),
                retrievedSet.getProfileId(),
                retrievedSet.getGearClass(),
                gearItemsRequestList));

        return new GearSetResponse(
                retrievedSet.getId(),
                retrievedSet.getGearClass(),
                averageItemLevel,
                itemListRequest);
    }

    public void deleteGearSetEquipment(Long gearSetId, List<Long> itemIdRequest) {
        GearSet retrievedSet = retrieveGearSetById(gearSetId);

        List<GearItems> filteredGearSet = retrievedSet.getEquippedItems().stream()
                .filter(gI -> !itemIdRequest.contains(gI.getItemId()))
                .collect(Collectors.toList());

        gearSetDao.save(new GearSet(
                retrievedSet.getId(),
                retrievedSet.getProfileId(),
                retrievedSet.getGearClass(),
                filteredGearSet));
    }

    public GearSet retrieveGearSetById(Long gearSetId) {
        return gearSetDao.findById(gearSetId)
                .orElseThrow(new InvalidIdException(
                        String.format("Gear-Set with the requested Id:%d not found.", gearSetId),
                        HttpStatus.BAD_REQUEST
                ));
    }

    private int calculateAverageItemLevels(List<Item> equippedItems) {
        //In: Item -> equippedItems.get(index).getItemLevel()
        double averageItemLevels = equippedItems.stream().map(Item::getItemLevel)
                .mapToDouble(n -> n)
                .average()
                .orElse(0.0);
        return (int)floor(averageItemLevels);
    }

    public List<GearSetResponse> getGearSetsForProfileId(Long profileId) {
        List<GearSet> profileGearSets = gearSetDao.findByProfileId(profileId);
        return profileGearSets.stream()
                .map(gS -> {
                    List<Item> gearSetItems = mapGearItemsToItemIds(gS.getEquippedItems());
                    return new GearSetResponse(
                            gS.getId(),
                            gS.getGearClass(),
                            calculateAverageItemLevels(gearSetItems),
                            gearSetItems);
                })
                .collect(Collectors.toList());
    }

    public List<Item> getItemsByGearSetClassAndLevel(Long gearSetId, int level) {
        GearSet retrievedGearSet = gearSetDao.findById(gearSetId)
                .orElseThrow(new InvalidIdException(
                        String.format("GearSet with Id:%d not found.",gearSetId),HttpStatus.BAD_REQUEST));

        CharacterClass desiredGearClass = retrievedGearSet.getGearClass();

        return itemService.getItemsByCategoryAndLevel(desiredGearClass.getAbbreviation(), level);
    }

    private List<Item> mapGearItemsToItemIds(List<GearItems> gearItems) {
        List<Long> itemIds = gearItems.stream()
                .map(GearItems::getItemId)
                .collect(Collectors.toList());
        return itemService.getItemsByIds(itemIds);
    }
}
