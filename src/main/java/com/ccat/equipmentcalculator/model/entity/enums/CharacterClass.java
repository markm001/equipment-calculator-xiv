package com.ccat.equipmentcalculator.model.entity.enums;

public enum CharacterClass {
    PALADIN (ClassJobCategory.PLD),
    WARRIOR (ClassJobCategory.WAR),
    DARKKNIGHT (ClassJobCategory.DRK),
    GUNBREAKER (ClassJobCategory.GNB),

    MONK (ClassJobCategory.MNK),
    DRAGOON (ClassJobCategory.DRG),
    NINJA (ClassJobCategory.NIN),
    SAMURAI (ClassJobCategory.SAM),
    REAPER (ClassJobCategory.RPR),

    WHITEMAGE (ClassJobCategory.WHM),
    SCHOLAR (ClassJobCategory.SCH),
    ASTROLOGIAN (ClassJobCategory.AST),
    SAGE (ClassJobCategory.SGE),

    BARD (ClassJobCategory.BRD),
    MACHINIST (ClassJobCategory.MCH),
    DANCER (ClassJobCategory.DNC),

    BLACKMAGE (ClassJobCategory.BLM),
    SUMMONER (ClassJobCategory.SMN),
    REDMAGE (ClassJobCategory.RDM),
    BLUEMAGE (ClassJobCategory.BLU),

    CARPENTER (ClassJobCategory.CRP),
    BLACKSMITH (ClassJobCategory.BSM),
    ARMORER (ClassJobCategory.ARM),
    GOLDSMITH (ClassJobCategory.GSM),
    LEATHERWORKER (ClassJobCategory.LTW),
    WEAVER (ClassJobCategory.WVR),
    ALCHEMIST (ClassJobCategory.ALC),
    CULINARIAN (ClassJobCategory.CUL),

    MINER (ClassJobCategory.MIN),
    BOTANIST (ClassJobCategory.BTN),
    FISHER (ClassJobCategory.FSH),

    ARCANIST (ClassJobCategory.ACN),
    CONJURER (ClassJobCategory.CNJ),
    GLADIATOR (ClassJobCategory.GLA),
    LANCER (ClassJobCategory.LNC),
    MARAUDER (ClassJobCategory.MRD),
    PUGILIST (ClassJobCategory.PGL),
    ROGUE (ClassJobCategory.ROG),
    ARCHER (ClassJobCategory.ARC),
    THAUMATURGE (ClassJobCategory.THM),
    ADVENTURER (ClassJobCategory.ADV);

    private final ClassJobCategory abbreviation;

    private CharacterClass(ClassJobCategory abbreviation) {
        this.abbreviation = abbreviation;
    }

    public ClassJobCategory getAbbreviation() {
        return this.abbreviation;
    }
}
