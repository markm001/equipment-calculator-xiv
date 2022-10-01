package com.ccat.equipmentcalculator.controller;

import com.ccat.equipmentcalculator.model.Entity.CharacterClass;
import com.ccat.equipmentcalculator.model.Entity.CharacterProfile;
import com.ccat.equipmentcalculator.model.repository.CharacterProfileDao;
import com.ccat.equipmentcalculator.model.service.CharacterProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CharacterProfileController {
    private final CharacterProfileService characterProfileService;

    public CharacterProfileController(CharacterProfileService characterProfileService) {
        this.characterProfileService = characterProfileService;
    }

    @RequestMapping("/profiles/{id}")
    public ResponseEntity<CharacterProfile> getCharacterProfileById(@PathVariable(name="id") Long characterId) {
        Optional<CharacterProfile> characterPrfileResponse = characterProfileService.getCharacterProfileById(characterId);
        if(characterPrfileResponse.isPresent()) {
            return ResponseEntity.ok(characterPrfileResponse.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/profiles")
    public CharacterProfile createEmptyCharacterProfileWithClassAndLevel(
            @RequestBody CharacterProfile CharacterProfileRequest) {
        return characterProfileService.createCharacterProfileWithClassAndLevel(CharacterProfileRequest);
    }

    //TODO: Add PUT mapping & Statcalc implementation
}
