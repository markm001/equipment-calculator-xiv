package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.model.Entity.GearSet;
import com.ccat.equipmentcalculator.model.Entity.Item;
import com.ccat.equipmentcalculator.model.Entity.ItemSlot;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class GearSetService {
    private GearSetDao gearSetDao;
    private ItemDao itemDao;

    public GearSetService(GearSetDao gearSetDao, ItemDao itemDao) {
        this.gearSetDao = gearSetDao;
        this.itemDao = itemDao;
    }

    public GearSet createEmptyGearSet(GearSet gearSetRequest) {

        //TODO: Extract creation of Equipment-Set into separate Entity | ItemSlot: ItemId
        HashMap<ItemSlot, Item> equippedItem = new HashMap<>();
//        equippedItem.put(gearSetRequest.getEquipmentSlot(), gearSetRequest.getItemId());

        GearSet savedGearSet = new GearSet(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                gearSetRequest.getCharacterLevel(),
                equippedItem);
        gearSetDao.save(savedGearSet);

        return savedGearSet;
    }

    public Optional<GearSet> getGearSetById(Long setId) {
        return gearSetDao.findById(setId);
    }


}
