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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EquipmentList that = (EquipmentList) o;

        if (primary != null ? !primary.equals(that.primary) : that.primary != null) return false;
        return secondary != null ? secondary.equals(that.secondary) : that.secondary == null;
    }

    @Override
    public int hashCode() {
        int result = primary != null ? primary.hashCode() : 0;
        result = 31 * result + (secondary != null ? secondary.hashCode() : 0);
        return result;
    }
}
