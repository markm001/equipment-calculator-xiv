package com.ccat.equipmentcalculator.controller;

import com.ccat.equipmentcalculator.model.entity.GearSet;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {
    private final ItemDao itemDao;

    public ItemController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @RequestMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable(name="id") Long itemId) {
        Optional<Item> retrievedItem = itemDao.findById(itemId);
        if(retrievedItem.isPresent()) {
            return ResponseEntity.ok(retrievedItem.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping("/items")
    public List<Item> getItemsByListIds(@RequestBody List<Long> itemIds) {
        return itemDao.findByIds(itemIds);
    }

}
