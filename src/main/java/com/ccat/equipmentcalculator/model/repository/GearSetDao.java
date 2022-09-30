package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.Entity.EquipmentList;
import com.ccat.equipmentcalculator.model.Entity.GearSet;
import com.ccat.equipmentcalculator.model.Entity.Item;
import com.ccat.equipmentcalculator.model.Entity.ItemSlot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class GearSetDao {
    private List<GearSet> gearSetList = new ArrayList<>();

    public GearSetDao(ItemDao itemDao) {
        HashMap<ItemSlot, Item> equipMap = new HashMap<>();
        equipMap.put(ItemSlot.PRIMARY, itemDao.findById(1L).get());

        gearSetList.add(new GearSet(1L, 90, new EquipmentList()));
        gearSetList.add(new GearSet(2L, 90, new EquipmentList(1L,2L)));
    }

    public GearSet save(GearSet gearSet) {
        gearSetList.add(gearSet);
        return gearSet;
    }

    public Optional<GearSet> findById(Long setId) {
        return gearSetList.stream()
                .filter(gs -> gs.getId().equals(setId))
                .findFirst();
    }

    public GearSet update(GearSet gearSetRequest) {
        gearSetList = gearSetList.stream()
                .map(s -> s.getId().equals(gearSetRequest.getId())? gearSetRequest: s)
                .collect(Collectors.toList());
        return gearSetRequest;
    }
}
