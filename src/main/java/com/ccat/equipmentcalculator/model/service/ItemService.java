package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.client.XivApiClient;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private XivApiClient xivClient;

    public ItemService(XivApiClient xivClient) {
        this.xivClient = xivClient;
    }

    public Item getItemById(Long itemId) {
        return xivClient.getItemById(itemId);
    }

    public List<Item> getItemsByCategoryAndLevel(ClassJobCategory category, int level) {
        return xivClient.getItemsByCategoryAndLevel(category,level);
    }
}
