package com.ccat.equipmentcalculator.controller;

import com.ccat.equipmentcalculator.client.XivApiClient;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import com.ccat.equipmentcalculator.model.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("/items/{id}")
    public Item getItemById(@PathVariable(name="id") Long itemId) {
        return itemService.getItemById(itemId);
    }

//    @RequestMapping("/items")
//    public List<Item> getItemsByListIds(@RequestBody List<Long> itemIds) {
//        return itemDao.findByIds(itemIds);
//    }
}
