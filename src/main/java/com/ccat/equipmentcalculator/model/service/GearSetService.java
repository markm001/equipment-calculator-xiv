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

    public Optional<GearSetResponse> getGearSetById(Long setId) {
        Optional<GearSet> retrievedSet = gearSetDao.findById(setId);

        if(retrievedSet.isPresent()) {
            GearSet gearSet = retrievedSet.get();

                    GearSetResponse gearSetResponse = new GearSetResponse(
                            gearSet.getId(),
                            gearSet.getItemLevel(),
                            List.of(
                                getItemForId(gearSet.getEquippedItems().getPrimary()),
                                getItemForId(gearSet.getEquippedItems().getSecondary())
                    ));

            // calculate Stats for Set:
            System.out.println(calculateAverageItemLevels(gearSetResponse.getEquippedItems()));

            return Optional.of(gearSetResponse);
        }
        else {
            return Optional.empty();
        }
    }


    //TODO: Embed Items into GearSet instead of Ids - prevent API-Rate-Cap for requesting Data
    public Optional<GearSet> updateGearSetEquipment(Long gearSetId, EquipmentList equipmentList) {
        Optional<GearSet> retrievedSet = gearSetDao.findById(gearSetId);

        if(retrievedSet.isPresent()) {
            GearSet retrievedGearSet = retrievedSet.get();

            EquipmentList equipmentListRequest = new EquipmentList(
                    (equipmentList.getPrimary()!= null)? equipmentList.getPrimary() : retrievedGearSet.getEquippedItems().getPrimary(),
                    (equipmentList.getSecondary() != null)? equipmentList.getSecondary() : retrievedGearSet.getEquippedItems().getSecondary());

            int calculatedItemLevel = calculateAverageItemLevels(List.of(
                    getItemForId(equipmentListRequest.getPrimary()),
                    getItemForId(equipmentListRequest.getSecondary())));

            GearSet gearSetRequest = new GearSet(
                    retrievedGearSet.getId(),
                    retrievedGearSet.getGearClass(),
                    calculatedItemLevel,
                    equipmentListRequest);

            return Optional.of(gearSetDao.update(gearSetRequest));
        }
        return Optional.empty();
    }

    //Calculate average ItemLevels:
    private int calculateAverageItemLevels(List<Item> equippedItems) {
        //In: Item -> equippedItems.get(index).getItemLevel()
        Double averageItemLevels = equippedItems.stream().map(item -> item.getItemLevel())
                .mapToDouble(n -> n)
                .average()
                .orElse(0.0);
        return (int)floor(averageItemLevels);
    }

    //Get Item for Id:
    private Item getItemForId(Long itemId) {
        return itemDao.findById(itemId).orElse(new Item());
    }
}
