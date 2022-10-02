package com.ccat.equipmentcalculator.controller;

import com.ccat.equipmentcalculator.model.entity.EquipmentList;
import com.ccat.equipmentcalculator.model.entity.GearSet;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.service.GearSetService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GearSetController {
    private final GearSetService gearSetService;

    public GearSetController(GearSetService gearSetService) {
        this.gearSetService = gearSetService;
    }

    @PostMapping("/gearset")
    public GearSet createEmptyGearSet(@RequestBody GearSet gearSetRequest) {
        return gearSetService.createEmptyGearSet(gearSetRequest);
    }

    //TODO: Implement PutMapping for adding List of Equipment Ids into EquipmentList(overwrite most recent Item)

    @PutMapping("/gearset/{id}")
    public GearSet updateGearSetEquipment(
            @PathVariable(name="id") Long gearSetId,
            @RequestBody EquipmentList equipmentList) {
        GearSet gearSetResponse = gearSetService.updateGearSetEquipment(gearSetId, equipmentList);
        return new GearSet(
                gearSetResponse.getId(),
                gearSetResponse.getGearClass(),
                gearSetResponse.getItemLevel(),
                gearSetResponse.getEquippedItems());
    }

    @RequestMapping("/gearset/{id}")
    public GearSetResponse getGearSetById(@PathVariable(name="id") Long setId) {
        return gearSetService.getGearSetById(setId);
    }
}
