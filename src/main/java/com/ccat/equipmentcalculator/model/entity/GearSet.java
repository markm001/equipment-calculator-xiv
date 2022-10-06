package com.ccat.equipmentcalculator.model.entity;

import java.util.List;

public class GearSet {
    private Long id;

    private Long profileId;

    // extracted from Profile
    private CharacterClass gearClass;

    private List<GearItems> equippedItems;

    //Constructors:
    public GearSet() {

    }
    public GearSet(Long id, Long profileId, CharacterClass gearClass, List<GearItems> equippedItems) {
        this.id = id;
        this.profileId = profileId;
        this.gearClass = gearClass;
        this.equippedItems = equippedItems;
    }

    //Getters:
    public Long getId() {
        return id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public CharacterClass getGearClass() {
        return gearClass;
    }

    public List<GearItems> getEquippedItems() {
        return equippedItems;
    }
}
