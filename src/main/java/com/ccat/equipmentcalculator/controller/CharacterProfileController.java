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
    public CharacterProfile getCharacterProfileById(@PathVariable(name="id") Long characterId) {
        CharacterProfile characterProfile = characterProfileService.getCharacterProfileById(characterId);
        return new CharacterProfile(characterProfile.getId(),
                characterProfile.getCharacterClass(),
                characterProfile.getLevel(),
                characterProfile.getGearSetId(),
                characterProfile.getStatBlock());
    }

    @PostMapping("/profiles")
    public CharacterProfile createEmptyCharacterProfileWithClassAndLevel(
            @RequestBody CharacterProfile CharacterProfileRequest) {
        return characterProfileService.createCharacterProfileWithClassAndLevel(CharacterProfileRequest);
    }

    @PutMapping("/profiles/{id}")
    public CharacterProfile updateCharacterProfileWithGearSetId(
            @PathVariable(name="id") Long profileId,
            @RequestBody CharacterProfile characterProfileRequest) {
        return characterProfileService.updateCharacterProfileGearSet(profileId,characterProfileRequest);
    }
}
