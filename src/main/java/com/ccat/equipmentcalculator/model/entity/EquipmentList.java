package com.ccat.equipmentcalculator.model.entity;

import java.util.LinkedList;
import java.util.List;

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

    public List<Long> getAllItems() {
        return List.of(
                (primary!=null)?primary: 0,
                (secondary!=null)?secondary: 0);
    }
}
