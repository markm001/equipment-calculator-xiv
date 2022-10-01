package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.Entity.CharacterProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CharacterProfileDao {
    List<CharacterProfile> characterProfileList = new ArrayList<>();
    public Optional<CharacterProfile> findById(Long characterId) {
        return  characterProfileList.stream()
                .filter(cp -> cp.getId().equals(characterId))
                .findFirst();
    }

    public CharacterProfile save(CharacterProfile characterProfileRequest) {
        characterProfileList.add(characterProfileRequest);
        return characterProfileRequest;
    }
}
