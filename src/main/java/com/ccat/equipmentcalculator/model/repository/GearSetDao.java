package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.entity.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class GearSetDao {
    private List<GearSet> gearSetList = new ArrayList<>();

    public GearSetDao(ItemDao itemDao) {
        gearSetList.add(new GearSet(1L,1L,CharacterClass.PALADIN,List.of()));
        gearSetList.add(new GearSet(2L, 1L, CharacterClass.PALADIN,
                List.of(
                        new GearItems(
                                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                                ItemSlot.PRIMARY,
                                1L),
                        new GearItems(
                                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                                ItemSlot.SECONDARY,
                                2L)
                )));
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

    public List<GearSet> findByProfileId(Long profileId) {
        return gearSetList.stream()
                .filter(s -> s.getProfileId().equals(profileId))
                .collect(Collectors.toList());
    }
}
