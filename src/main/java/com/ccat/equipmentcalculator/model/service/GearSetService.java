package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.exception.InvalidIdException;
import com.ccat.equipmentcalculator.exception.InvalidItemSlotException;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.entity.GearItems;
import com.ccat.equipmentcalculator.model.entity.GearSet;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.ItemSlot;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.floor;

@Service
public class GearSetService {
    private GearSetDao gearSetDao;
    private ItemDao itemDao;

    public GearSetService(GearSetDao gearSetDao, ItemDao itemDao) {
        this.gearSetDao = gearSetDao;
        this.itemDao = itemDao;
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

        List<Item> retrievedItems = itemDao.findByIds(itemIds);

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

    public GearSetResponse updateGearSetEquipment(Long gearSetId, List<GearItems> gearItems) {
        // check GearSet validity:
        GearSet retrievedSet = retrieveGearSetById(gearSetId);

        // retrieved GearSet Items:
        List<Long> savedItemIds = retrievedSet.getEquippedItems().stream()
                .map(GearItems::getItemId)
                .collect(Collectors.toList());

        List<Item> savedItems = itemDao.findByIds(savedItemIds);

        // check ItemList validity:
        List<Long> itemIdList = gearItems.stream()
                .map(GearItems::getItemId)
                .collect(Collectors.toList());
        List<Item> retrievedList = itemDao.findByIds(itemIdList);

        //TODO: implement ClassJobCategory for Item-Entity constraint  {
        // || check only ItemList for Job -> throw: InvalidItemId Exception (better!) }

        HashMap<ItemSlot, Item> validSlotItems = new HashMap<>();
        //check Item Slots:
        for(ItemSlot slot : ItemSlot.values()) {
            Item slotItem = retrievedList.stream()
                    .filter(i -> i.getItemSlot().equals(slot))
                    .findFirst()
                    .orElse(
                            savedItems.stream()
                                    .filter(i -> i.getItemSlot().equals(slot)).findFirst()
                                    .orElse(new Item())); //fill slot with Empty
            validSlotItems.put(slot, slotItem);
        }

        List<Item> slotMapItems = new ArrayList<>(validSlotItems.values());
        int averageItemLevel = calculateAverageItemLevels(slotMapItems);

        List<GearItems> gearItemList = slotMapItems.stream()
                .map(sI -> new GearItems(
                        UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                        sI.getItemSlot(),
                        sI.getId()))
                .collect(Collectors.toList());

        gearSetDao.update(new GearSet(
                retrievedSet.getId(),
                retrievedSet.getProfileId(),
                retrievedSet.getGearClass(),
                gearItemList
                ));

        return new GearSetResponse(
                retrievedSet.getId(),
                retrievedSet.getGearClass(),
                averageItemLevel,
                slotMapItems);
    }

    public void deleteGearSetEquipment(Long gearSetId, List<Long> itemIdRequest) {
        GearSet retrievedSet = retrieveGearSetById(gearSetId);

        List<GearItems> filteredGearSet = retrievedSet.getEquippedItems().stream()
                .filter(gI -> !itemIdRequest.contains(gI.getItemId()))
                .collect(Collectors.toList());

        gearSetDao.update(new GearSet(
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

    private List<Item> mapGearItemsToItemIds(List<GearItems> gearItems) {
        List<Long> itemIds = gearItems.stream()
                .map(GearItems::getItemId)
                .collect(Collectors.toList());
        return itemDao.findByIds(itemIds);
    }
}
