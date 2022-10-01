package com.ccat.equipmentcalculator.model.Entity;

import java.util.HashMap;

public class CharacterProfile {
    Long id;
    CharacterClass characterClass;
    int level;
    Long gearSetId;

    //TODO: Replace with StatBlock Class
    HashMap<String, Integer> statBlock;

    public CharacterProfile() {

    }

    public CharacterProfile(CharacterClass characterClass, int level) {
        this.characterClass = characterClass;
        this.level = level;
    }

    public CharacterProfile(Long gearSetId) {
        this.gearSetId = gearSetId;
    }

    public CharacterProfile(Long id, CharacterClass characterClass, int level, Long gearSetId, HashMap<String, Integer> statBlock) {
        this.id = id;
        this.characterClass = characterClass;
        this.level = level;
        this.gearSetId = gearSetId;
        this.statBlock = statBlock;
    }

    public Long getId() {
        return id;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public int getLevel() {
        return level;
    }

    public Long getGearSetId() {
        return gearSetId;
    }

    public HashMap<String, Integer> getStatBlock() {
        return statBlock;
    }
}
