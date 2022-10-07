package com.ccat.equipmentcalculator.controller;

import com.ccat.equipmentcalculator.model.CharacterProfileResponse;
import com.ccat.equipmentcalculator.model.entity.CharacterProfile;
import com.ccat.equipmentcalculator.model.service.CharacterProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
public class CharacterProfileController {
    private final CharacterProfileService characterProfileService;

    public CharacterProfileController(CharacterProfileService characterProfileService) {
        this.characterProfileService = characterProfileService;
    }

    @RequestMapping("/profiles/{id}")
    public CharacterProfileResponse getCharacterProfileById(@PathVariable(name="id") Long characterId) {
        return characterProfileService.getCharacterProfileById(characterId);
    }

    @PostMapping("/profiles")
    public CharacterProfile createEmptyCharacterProfileWithClassAndLevel(
            @RequestBody CharacterProfile CharacterProfileRequest) {
        return characterProfileService.createCharacterProfileWithClassAndLevel(CharacterProfileRequest);
    }

    @PutMapping("/profiles/{id}")
    public CharacterProfile updateCharacterProfileWithClassAndLevel(
            @PathVariable(name="id") Long profileId,
            @RequestBody CharacterProfile profileRequest) {
        return characterProfileService.updateCharacterProfile(profileId, profileRequest);
    }
}
