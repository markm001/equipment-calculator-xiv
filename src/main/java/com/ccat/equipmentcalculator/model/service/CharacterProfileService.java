package com.ccat.equipmentcalculator.model.service;

import com.ccat.equipmentcalculator.model.Entity.CharacterClass;
import com.ccat.equipmentcalculator.model.Entity.CharacterProfile;
import com.ccat.equipmentcalculator.model.Entity.GearSet;
import com.ccat.equipmentcalculator.model.Entity.Item;
import com.ccat.equipmentcalculator.model.repository.CharacterProfileDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CharacterProfileService {
    private final CharacterProfileDao characterProfileDao;

    public CharacterProfileService(CharacterProfileDao characterProfileDao) {
        this.characterProfileDao = characterProfileDao;
    }

    public Optional<CharacterProfile> getCharacterProfileById(Long characterId) {
        return characterProfileDao.findById(characterId);
    }

    public CharacterProfile createCharacterProfileWithClassAndLevel(CharacterProfile characterProfileRequest) {
        return characterProfileDao.save(new CharacterProfile(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                characterProfileRequest.getCharacterClass(),
                characterProfileRequest.getLevel(),
                0L,
                new HashMap<>()));
    }

    //Calculate StatBlock:
    private void calculateProfileStats(List<Item> equippedItems) {
        //In: HashMap<String, Integer> statMap; "Strength": 100
        HashMap<String,Integer> combinedStats = new HashMap<>();

        for(Item i : equippedItems) {
            if(i.getStatMap() == null) return;
            for (String statKey : i.getStatMap().keySet()) {
                addStats(combinedStats, statKey, i.getStatMap().get(statKey));
            }
        }

        System.out.println(combinedStats);
    }

    private void addStats(HashMap<String, Integer> statCalc, String statKey, Integer value) {
        if (statCalc.containsKey(statKey)) {
            statCalc.put(statKey, statCalc.get(statKey) + value);
        } else {
            statCalc.put(statKey, value);
        }
    }
}
