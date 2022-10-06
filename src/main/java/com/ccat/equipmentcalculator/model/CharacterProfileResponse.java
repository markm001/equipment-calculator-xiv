package com.ccat.equipmentcalculator.model;

import com.ccat.equipmentcalculator.model.entity.CharacterClass;
import com.ccat.equipmentcalculator.model.entity.StatBlock;

import java.util.HashMap;
import java.util.List;

public class CharacterProfileResponse {
    Long id;
    CharacterClass characterClass;
    int level;
    List<GearSetResponse> gearSetResponse;
    StatBlock statBlock;

    //Constructors:
    public CharacterProfileResponse() {

    }

    public CharacterProfileResponse(Long id, CharacterClass characterClass, int level, List<GearSetResponse> gearSetResponse, StatBlock statBlock) {
        this.id = id;
        this.characterClass = characterClass;
        this.level = level;
        this.gearSetResponse = gearSetResponse;
        this.statBlock = statBlock;
    }

    //Getters:
    public Long getId() {
        return id;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public int getLevel() {
        return level;
    }

    public List<GearSetResponse> getGearSetResponse() {
        return gearSetResponse;
    }

    public StatBlock getStatBlock() {
        return statBlock;
    }
}
