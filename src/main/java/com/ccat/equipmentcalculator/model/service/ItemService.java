package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.client.XivApiClient;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemDao itemDao;
    private XivApiClient xivClient;

    public ItemService(ItemDao itemDao, XivApiClient xivClient) {
        this.xivClient = xivClient;
        this.itemDao = itemDao;
    }

    public Optional<Item> getItemById(Long itemId) {
        return itemDao.findById(itemId);
    }

    public List<Item> getItemsByIds(List<Long> itemIds) {
        return itemDao.findByIds(itemIds);
    }

    public List<Item> getItemsByCategoryAndLevel(ClassJobCategory category, int level) {
//        return itemDao.findByCategoryAndLevel(category,level);
        return xivClient.getItemsByCategoryAndLevel(category,level);
    }
}
