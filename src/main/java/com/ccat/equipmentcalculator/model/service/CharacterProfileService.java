package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.exception.InvalidIdException;
import com.ccat.equipmentcalculator.model.Entity.CharacterClass;
import com.ccat.equipmentcalculator.model.Entity.CharacterProfile;
import com.ccat.equipmentcalculator.model.Entity.GearSet;
import com.ccat.equipmentcalculator.model.Entity.Item;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.repository.CharacterProfileDao;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class CharacterProfileService {
    private final CharacterProfileDao characterProfileDao;
    private final GearSetService gearSetService;

    public CharacterProfileService(CharacterProfileDao characterProfileDao, GearSetService gearSetService) {
        this.characterProfileDao = characterProfileDao;
        this.gearSetService = gearSetService;
    }

    public CharacterProfile getCharacterProfileById(Long characterId) {
        return characterProfileDao.findById(characterId)
                .orElseThrow(new InvalidIdException(
                        String.format("No profile with the Id: %d was found", characterId), HttpStatus.BAD_REQUEST));
    }

    public CharacterProfile createCharacterProfileWithClassAndLevel(CharacterProfile characterProfileRequest) {
        return characterProfileDao.save(new CharacterProfile(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                characterProfileRequest.getCharacterClass(),
                characterProfileRequest.getLevel(),
                0L,
                new HashMap<>()));
    }

    public CharacterProfile updateCharacterProfileGearSet(Long profileId, CharacterProfile characterProfileRequest) {
        CharacterProfile retrievedProfile = characterProfileDao.findById(profileId).orElseThrow(
                new InvalidIdException(
                        String.format("A Profile with the requested Id: %d doesn't exist.",profileId)
                        , HttpStatus.BAD_REQUEST));

        GearSetResponse gearSetResponse = gearSetService.getGearSetById(characterProfileRequest.getGearSetId());

            return characterProfileDao.update(new CharacterProfile(
                    retrievedProfile.getId(),
                    retrievedProfile.getCharacterClass(),
                    retrievedProfile.getLevel(),
                    gearSetResponse.getId(),
                    calculateProfileStatBlock(gearSetResponse.getEquippedItems())));
    }

    //Calculate StatBlock:
    private HashMap<String, Integer> calculateProfileStatBlock(List<Item> equippedItems) {
        //In: HashMap<String, Integer> statMap; "Strength": 100
        HashMap<String,Integer> combinedStats = new HashMap<>();

        for(Item i : equippedItems) {
            if(i.getStatMap() == null) return combinedStats;
            for (String statKey : i.getStatMap().keySet()) {
                addStats(combinedStats, statKey, i.getStatMap().get(statKey));
            }
        }

        return combinedStats;
    }

    private void addStats(HashMap<String, Integer> statCalc, String statKey, Integer value) {
        if (statCalc.containsKey(statKey)) {
            statCalc.put(statKey, statCalc.get(statKey) + value);
        } else {
            statCalc.put(statKey, value);
        }
    }
}
