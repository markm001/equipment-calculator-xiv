package com.ccat.equipmentcalculator.model.entity.enums;

public enum CharacterClass {
    EMPTY (ClassJobCategory.ACN),
    PALADIN (ClassJobCategory.PLD),
    WARRIOR (ClassJobCategory.WAR);

    private final ClassJobCategory abbreviation;

    private CharacterClass(ClassJobCategory abbreviation) {
        this.abbreviation = abbreviation;
    }

    public ClassJobCategory getAbbreviation() {
        return this.abbreviation;
    }
}
