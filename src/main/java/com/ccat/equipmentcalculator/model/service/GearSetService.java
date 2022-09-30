package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.model.Entity.EquipmentList;
import com.ccat.equipmentcalculator.model.Entity.GearSet;
import com.ccat.equipmentcalculator.model.Entity.Item;
import com.ccat.equipmentcalculator.model.Entity.ItemSlot;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
                gearSetRequest.getCharacterLevel(),
                new EquipmentList());
        gearSetDao.save(savedGearSet);

        return savedGearSet;
    }

    public Optional<GearSetResponse> getGearSetById(Long setId) {
        Optional<GearSet> retrievedSet = gearSetDao.findById(setId);

        if(retrievedSet.isPresent()) {
            GearSet gearSet = retrievedSet.get();

            //retrieve Item responses by Id:
            Optional<Item> primaryItem = itemDao.findById(gearSet.getEquippedItem().getPrimary());
            Optional<Item> secondaryItem = itemDao.findById(gearSet.getEquippedItem().getSecondary());

            GearSetResponse gearSetResponse = new GearSetResponse(gearSet.getId(),
                    gearSet.getCharacterLevel(),
                    List.of(
                            primaryItem.orElse(new Item()),
                            secondaryItem.orElse(new Item())
                    ));

            // calculate Stats for Set:
            calculateGearSetStats(gearSetResponse.getEquippedItems());

            return Optional.of(gearSetResponse);
        }
        else {
            return Optional.empty();
        }
    }


    public Optional<GearSet> updateGearSetEquipment(Long gearSetId, EquipmentList equipmentList) {
        Optional<GearSet> retrievedSet = gearSetDao.findById(gearSetId);
        if(retrievedSet.isPresent()) {
            GearSet retrievedGearSet = retrievedSet.get();
            GearSet gearSetRequest = new GearSet(
                    retrievedGearSet.getId(),
                    retrievedGearSet.getCharacterLevel(),
                    new EquipmentList(
                            (equipmentList.getPrimary()!= null)? equipmentList.getPrimary() : retrievedGearSet.getEquippedItem().getPrimary(),
                            (equipmentList.getSecondary() != null)? equipmentList.getSecondary() : retrievedGearSet.getEquippedItem().getSecondary()
                    ));

            return Optional.of(gearSetDao.update(gearSetRequest));
        }
        return Optional.empty();
    }

    private void calculateGearSetStats(List<Item> equippedItems) {
        //In: HashMap<String, Integer> statMap; "Strength": 100
        HashMap<String,Integer> combinedStats = new HashMap<>();

        for(Item i : equippedItems) {
            if(i.getStatMap() == null) return;
            for (String statKey : i.getStatMap().keySet()) {
                addStats(combinedStats, statKey, i.getStatMap().get(statKey));
            }
        }

        System.out.println(combinedStats);
    }

    private void addStats(HashMap<String, Integer> statCalc, String statKey, Integer value) {
        if (statCalc.containsKey(statKey)) {
            statCalc.put(statKey, statCalc.get(statKey) + value);
        } else {
            statCalc.put(statKey, value);
        }
    }
}
