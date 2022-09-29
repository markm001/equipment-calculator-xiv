package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.Entity.GearSet;
import com.ccat.equipmentcalculator.model.Entity.Item;
import com.ccat.equipmentcalculator.model.Entity.ItemSlot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class GearSetDao {
    private List<GearSet> gearSetList = new ArrayList<>();

    public GearSetDao(ItemDao itemDao) {
        HashMap<ItemSlot, Item> equipMap = new HashMap<>();
        equipMap.put(ItemSlot.PRIMARY, itemDao.findById(1L).get());

        gearSetList.add(new GearSet(1L, 90, equipMap));
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
}
