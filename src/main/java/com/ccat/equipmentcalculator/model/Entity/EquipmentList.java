package com.ccat.equipmentcalculator.model.Entity;

public class EquipmentList {
    //Takes Item Ids:
    private Long primary;
    private Long secondary;

    //Constructors:
    public EquipmentList() {

    }
    public EquipmentList(Long primary, Long secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    //Getters:
    public Long getPrimary() {
        return primary;
    }

    public Long getSecondary() {
        return secondary;
    }
}
