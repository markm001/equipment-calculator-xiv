package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.exception.InvalidIdException;
import com.ccat.equipmentcalculator.model.CharacterProfileResponse;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.entity.CharacterProfile;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.StatBlock;
import com.ccat.equipmentcalculator.model.repository.CharacterProfileDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class CharacterProfileService {
    private final CharacterProfileDao characterProfileDao;
    private final GearSetService gearSetService;

    public CharacterProfileService(CharacterProfileDao characterProfileDao, GearSetService gearSetService) {
        this.characterProfileDao = characterProfileDao;
        this.gearSetService = gearSetService;
    }

    public CharacterProfile createCharacterProfileWithClassAndLevel(CharacterProfile characterProfileRequest) {
        return characterProfileDao.save(new CharacterProfile(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                characterProfileRequest.getCharacterClass(),
                new StatBlock()));
    }

    public CharacterProfile updateCharacterProfile(Long profileId, CharacterProfile profileRequest) {
        CharacterProfile retrievedProfile = retrieveCharacterProfile(profileId);

        CharacterProfile updatedProfile = new CharacterProfile(retrievedProfile.getId(),
                profileRequest.getCharacterClass(),
                new StatBlock()
        );

        return characterProfileDao.save(updatedProfile);
    }

    private CharacterProfile retrieveCharacterProfile(Long profileId) {
        return characterProfileDao.findById(profileId)
                .orElseThrow(
                        new InvalidIdException(String.format("Profile with Id:%d does not exist.", profileId),
                                HttpStatus.BAD_REQUEST));
    }


    public CharacterProfileResponse getCharacterProfileById(Long profileId) {

        CharacterProfile retrievedProfile = retrieveCharacterProfile(profileId);

        List<GearSetResponse> retrievedGearSets = gearSetService.getGearSetsForProfileId(retrievedProfile.getId());

        //Calculate StatBlock for specified Class (if nonexistent class is specified - calculate Empty Stats):
        StatBlock profileStatBlock = calculateProfileStatBlock(retrievedGearSets.stream()
                .filter(gS -> gS.getGearClass().equals(retrievedProfile.getCharacterClass()))
                .findFirst()
                .map(GearSetResponse::getEquippedItems)
                .orElse(List.of())
        );

        return new CharacterProfileResponse(
                retrievedProfile.getId(),
                retrievedProfile.getCharacterClass(),
                retrievedGearSets,
                profileStatBlock
                );
    }

    //Calculate StatBlock:
    public StatBlock calculateProfileStatBlock(List<Item> equippedItems) {
        //In: HashMap<String, Integer> statMap; "Strength": 100
        HashMap<String,Integer> combinedStats = new HashMap<>();

        for(Item i : equippedItems) {
            if(i.getStatMap() != null) {
                for (String statKey : i.getStatMap().keySet()) {
                    addStats(combinedStats, statKey, i.getStatMap().get(statKey));
                }
            }
        }

        return new StatBlock(
                combinedStats.getOrDefault("Strength", 0),
                combinedStats.getOrDefault("Dexterity", 0),
                combinedStats.getOrDefault("Mind", 0));
    }

    private void addStats(HashMap<String, Integer> statCalc, String statKey, Integer value) {
        if (statCalc.containsKey(statKey)) {
            statCalc.put(statKey, statCalc.get(statKey) + value);
        } else {
            statCalc.put(statKey, value);
        }
    }
}
