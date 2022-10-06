package com.ccat.equipmentcalculator.model.entity;

import javax.persistence.*;

@Entity
@Table(name="profiles")
public class CharacterProfile {
    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    CharacterClass characterClass;
    int level;

    @Transient
    StatBlock statBlock;

    //Constructors:
    public CharacterProfile() {

    }
    public CharacterProfile(CharacterClass characterClass, int level) {
        this.characterClass = characterClass;
        this.level = level;
    }
    public CharacterProfile(Long id, CharacterClass characterClass, int level, StatBlock statBlock) {
        this.id = id;
        this.characterClass = characterClass;
        this.level = level;
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

    public StatBlock getStatBlock() {
        return statBlock;
    }
}
