package com.ccat.equipmentcalculator.repository;

import com.ccat.equipmentcalculator.model.entity.CharacterProfile;
import com.ccat.equipmentcalculator.model.entity.GearItems;
import com.ccat.equipmentcalculator.model.entity.GearSet;
import com.ccat.equipmentcalculator.model.entity.StatBlock;
import com.ccat.equipmentcalculator.model.entity.enums.CharacterClass;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;
import com.ccat.equipmentcalculator.model.repository.CharacterProfileDao;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase
public class GearSetRepositoryTest {

    @Autowired
    private GearSetDao gearSetDao;
    @Autowired
    private CharacterProfileDao profileDao;

//    @BeforeEach
//    public void init() {
//        CharacterProfile profile1 = new CharacterProfile(
//                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
//                CharacterClass.WARRIOR,
//                90,
//                new StatBlock());
//        CharacterProfile profile2 = new CharacterProfile(
//                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
//                CharacterClass.PALADIN,
//                90,
//                new StatBlock());
//        profileDao.saveAll(List.of(profile1, profile2));
//    }

    @Test
    public void test_findGearSetByProfileId_returnListWithSingleGearSet() {
        //given
        CharacterProfile profile = generateProfile();
        profileDao.save(profile);
        GearSet setRequest = generateGearSet(profile);
        gearSetDao.save(setRequest);

        //when
        List<GearSet> setResponse = gearSetDao.findByProfileId(profile.getId());
        //then
        assertThat(setResponse.size()).isEqualTo(1);
        
//        assertThat(setResponse).containsExactly(setRequest);
        assertThat(setResponse.get(0))
                .usingRecursiveComparison()
                .ignoringFields("equippedItems")
                .isEqualTo(setRequest);
    }

    @Test
    public void test_findGearSetByProfileId_returnGearSetsForProfileId() {
        //given
        CharacterProfile profileRequest = generateProfile();
        profileDao.save(profileRequest);

        GearSet set1 = generateGearSet(profileRequest);
        GearSet set2 = generateGearSet(profileRequest);
        List<GearSet> setsToSave = List.of(set1, set2);
        gearSetDao.saveAll(setsToSave);

        //when
        List<GearSet> gearSetResponses = gearSetDao.findByProfileId(profileRequest.getId());

        //then
        assertThat(gearSetResponses.size()).isEqualTo(2);
        assertThat(gearSetResponses).usingRecursiveFieldByFieldElementComparatorIgnoringFields("equippedItems").containsExactlyInAnyOrderElementsOf(setsToSave);

    }

    private CharacterProfile generateProfile() {
        CharacterProfile profileRequest = new CharacterProfile(
                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                CharacterClass.PALADIN,
                new StatBlock());
        profileDao.save(profileRequest);
        return profileRequest;
    }

    private GearSet generateGearSet(CharacterProfile profile) {
        return new GearSet(
                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                profile.getId(),
                90,
                profile.getCharacterClass(),
                new ArrayList<>());
    }
}
