package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.exception.InvalidIdException;
import com.ccat.equipmentcalculator.exception.InvalidItemSlotException;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.entity.EquipmentList;
import com.ccat.equipmentcalculator.model.entity.GearSet;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.ItemSlot;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                gearSetRequest.getGearClass(),
                0,
                new EquipmentList());
        gearSetDao.save(savedGearSet);

        return savedGearSet;
    }

    public GearSetResponse getGearSetById(Long setId) {

        GearSet retrievedSet = gearSetDao.findById(setId)
                .orElseThrow(new InvalidIdException(
                        String.format("Gear-Set with requested Id: %d not found", setId), HttpStatus.BAD_REQUEST));

        GearSetResponse gearSetResponse = new GearSetResponse(
                retrievedSet.getId(),
                retrievedSet.getGearClass(),
                retrievedSet.getItemLevel(),
                itemDao.findByIds(retrievedSet.getEquippedItems().getAllItems())
        );

        // calculate Stats for Set:
        System.out.println(calculateAverageItemLevels(gearSetResponse.getEquippedItems()));

        return gearSetResponse;
    }


    //TODO: Embed Items into GearSet instead of Ids - prevent API-Rate-Cap for requesting Data
    public GearSet updateGearSetEquipment(Long gearSetId, EquipmentList equipmentList) {
        GearSet retrievedGearSet = gearSetDao.findById(gearSetId)
                .orElseThrow(new InvalidIdException(
                        String.format("Gear-Set with the requested Id: %d not found.",gearSetId),HttpStatus.BAD_REQUEST));

        EquipmentList equipmentListRequest = new EquipmentList(
                (equipmentList.getPrimary()!= null)? equipmentList.getPrimary() : retrievedGearSet.getEquippedItems().getPrimary(),
                (equipmentList.getSecondary() != null)? equipmentList.getSecondary() : retrievedGearSet.getEquippedItems().getSecondary());


        List<Item> itemResponseList = new ArrayList<>();

        //Check if Item is in correct EquipmentList-Slot:
        for(int i=0; i<ItemSlot.values().length; i++) {
            Long currentItemIndex = equipmentListRequest.getAllItems().get(i);
            Item retrievedItem = itemDao.findById(currentItemIndex).orElse(new Item());
            checkEquipmentSlot(retrievedItem, ItemSlot.values()[i]);

            itemResponseList.add(retrievedItem);
        }

        int calculatedItemLevel = calculateAverageItemLevels(itemResponseList);

        GearSet gearSetRequest = new GearSet(
                retrievedGearSet.getId(),
                retrievedGearSet.getGearClass(),
                calculatedItemLevel,
                equipmentListRequest);

        return gearSetDao.update(gearSetRequest);
    }

    //Calculate average ItemLevels:
    private int calculateAverageItemLevels(List<Item> equippedItems) {
        //In: Item -> equippedItems.get(index).getItemLevel()
        double averageItemLevels = equippedItems.stream().map(item -> item.getItemLevel())
                .mapToDouble(n -> n)
                .average()
                .orElse(0.0);
        return (int)floor(averageItemLevels);
    }

    private boolean checkEquipmentSlot(Item item, ItemSlot itemSlot) {
        if ((item.getItemSlot() == itemSlot) || item.getItemSlot() == null) {
            return true;
        } else throw new InvalidItemSlotException(
                String.format("Item with Id:%d and ItemSlot:%s cannot be equipped in the requested %s Slot.",
                        item.getId(), item.getItemSlot(), itemSlot.toString()),
                HttpStatus.BAD_REQUEST);
    }
}
