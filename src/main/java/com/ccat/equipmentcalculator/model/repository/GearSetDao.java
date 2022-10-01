package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.Entity.*;
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
        gearSetList.add(new GearSet(0L, CharacterClass.EMPTY, 0, new EquipmentList()));
        gearSetList.add(new GearSet(1L, CharacterClass.WARRIOR, 0, new EquipmentList()));
        gearSetList.add(new GearSet(2L, CharacterClass.PALADIN,0, new EquipmentList(1L,2L)));
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
