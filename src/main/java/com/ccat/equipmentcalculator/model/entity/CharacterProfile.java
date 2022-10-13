package com.ccat.equipmentcalculator.model.entity;

import com.ccat.equipmentcalculator.model.entity.enums.CharacterClass;

import javax.persistence.*;

@Entity
@Table(name="profiles")
public class CharacterProfile {
    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    CharacterClass characterClass;

    @Transient
    StatBlock statBlock;

    //Constructors:
    public CharacterProfile() {

    }

    public CharacterProfile(Long id, CharacterClass characterClass, StatBlock statBlock) {
        this.id = id;
        this.characterClass = characterClass;
        this.statBlock = statBlock;
    }

    //Getters:
    public Long getId() {
        return id;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public StatBlock getStatBlock() {
        return statBlock;
    }
}
