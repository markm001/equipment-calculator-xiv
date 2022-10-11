package com.ccat.equipmentcalculator.client;

import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;

import java.util.List;

public interface XivApiClient {
    public List<Item> getItemsByCategoryAndLevel(ClassJobCategory category, int level);
}
