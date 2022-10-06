package com.ccat.equipmentcalculator.controller;

import com.ccat.equipmentcalculator.model.entity.GearItems;
import com.ccat.equipmentcalculator.model.entity.GearSet;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.service.GearSetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GearSetController {
    private final GearSetService gearSetService;

    public GearSetController(GearSetService gearSetService) {
        this.gearSetService = gearSetService;
    }

    @PostMapping("/gearsets")
    public GearSet createEmptyGearSet(@RequestBody GearSet gearSetRequest) {
        return gearSetService.createEmptyGearSet(gearSetRequest);
    }

    @PutMapping("/gearsets/{id}/items")
    public GearSetResponse updateGearSetEquipmentWithListOfGearItems(
            @PathVariable(name="id") Long gearSetId,
            @RequestBody List<GearItems> gearItems) {
        return gearSetService.updateGearSetEquipment(gearSetId, gearItems);
    }

    @DeleteMapping("/gearsets/{id}/items")
    public ResponseEntity<Object> deleteGearSetEquipmentWithListOfItemIds(
            @PathVariable(name="id") Long gearSetId,
            @RequestBody List<Long> itemIdRequest) {
        gearSetService.deleteGearSetEquipment(gearSetId, itemIdRequest);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping("/gearsets/{id}")
    public GearSetResponse getGearSetById(@PathVariable(name="id") Long setId) {
        return gearSetService.getGearSetById(setId);
    }
}
