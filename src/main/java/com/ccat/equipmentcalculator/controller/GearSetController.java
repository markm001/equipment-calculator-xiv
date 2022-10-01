package com.ccat.equipmentcalculator.controller;

import com.ccat.equipmentcalculator.model.Entity.EquipmentList;
import com.ccat.equipmentcalculator.model.Entity.GearSet;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import com.ccat.equipmentcalculator.model.service.GearSetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<GearSet> updateGearSetEquipment(
            @PathVariable(name="id") Long gearSetId,
            @RequestBody EquipmentList equipmentList) {
        Optional<GearSet> gearSetResponse = gearSetService.updateGearSetEquipment(gearSetId, equipmentList);
        if(gearSetResponse.isPresent()) {
            return ResponseEntity.ok(gearSetResponse.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping("/gearset/{id}")
    public ResponseEntity<GearSetResponse> findGearSetById(@PathVariable(name="id") Long setId) {

        Optional<GearSetResponse> gearSetResponse = gearSetService.getGearSetById(setId);

        if(gearSetResponse.isPresent()) {
            return ResponseEntity.ok(gearSetResponse.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
