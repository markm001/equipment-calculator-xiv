package com.ccat.equipmentcalculator.model.entity;

public class StatBlock {
    private int strength;
    private int dexterity;
    private int mind;

    public StatBlock() {
    }

    public StatBlock(int strength, int dexterity, int mind) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.mind = mind;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getMind() {
        return mind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatBlock statBlock = (StatBlock) o;

        if (strength != statBlock.strength) return false;
        if (dexterity != statBlock.dexterity) return false;
        return mind == statBlock.mind;
    }

    @Override
    public int hashCode() {
        int result = strength;
        result = 31 * result + dexterity;
        result = 31 * result + mind;
        return result;
    }
}
