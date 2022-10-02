package com.ccat.equipmentcalculator.model;

import com.ccat.equipmentcalculator.model.entity.CharacterClass;

import java.util.HashMap;

public class CharacterProfileResponse {
    Long id;
    CharacterClass characterClass;
    int level;
    GearSetResponse gearSetResponse;
    HashMap<String, Integer> statBlock;

    //Constructors:
    public CharacterProfileResponse() {

    }

    public CharacterProfileResponse(Long id, CharacterClass characterClass, int level, GearSetResponse gearSetResponse, HashMap<String, Integer> statBlock) {
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

    public GearSetResponse getGearSetResponse() {
        return gearSetResponse;
    }

    public HashMap<String, Integer> getStatBlock() {
        return statBlock;
    }
}
