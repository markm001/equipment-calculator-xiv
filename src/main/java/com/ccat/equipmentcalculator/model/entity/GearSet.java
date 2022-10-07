package com.ccat.equipmentcalculator.model.entity;

import com.ccat.equipmentcalculator.model.entity.enums.CharacterClass;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="gearsets")
public class GearSet {
    @Id
    private Long id;

    private Long profileId;

    @Enumerated(EnumType.STRING)
    private CharacterClass gearClass;

    @ElementCollection
    @CollectionTable(name="GEARSET_ITEMS",
        joinColumns={@JoinColumn(name="gearsetId", referencedColumnName="ID")})
    private List<GearItems> equippedItems;

    //Constructors:
    public GearSet() {

    }
    public GearSet(Long id) {
        this.id = id;
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
