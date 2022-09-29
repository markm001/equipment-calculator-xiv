package com.ccat.equipmentcalculator.controller;

import com.ccat.equipmentcalculator.model.Entity.GearSet;
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

    //TODO: Implement PutMapping for adding Equipment into Slots

    @RequestMapping("/gearset/{id}")
    public ResponseEntity<GearSet> findGearSetById(@PathVariable(name="id") Long setId) {

        Optional<GearSet> gearSetResponse = gearSetService.getGearSetById(setId);

        if(gearSetResponse.isPresent()) {
            return ResponseEntity.ok(gearSetResponse.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
