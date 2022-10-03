package com.ccat.equipmentcalculator.service;

import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.ItemSlot;
import com.ccat.equipmentcalculator.model.entity.StatBlock;
import com.ccat.equipmentcalculator.model.repository.CharacterProfileDao;
import com.ccat.equipmentcalculator.model.service.CharacterProfileService;
import com.ccat.equipmentcalculator.model.service.GearSetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class CharacterProfileServiceTest {

    private CharacterProfileService service;

    @BeforeEach
    public void init() {
        this.service = new CharacterProfileService(
                mock(CharacterProfileDao.class),
                mock(GearSetService.class)
        );
    }

    @Test
    public void test_calculateStatBlockForItemWithEmptyStatMap_returnStatsWithoutEquipment() {
        //given
        Item testItem = saveItem(new HashMap<>());

        //when
        StatBlock result = service.calculateProfileStatBlock(List.of(testItem));
        //then
        assertThat(result).isEqualTo(new StatBlock());
    }

    @Test
    public void test_calculateStatBlockForOneItem_returnStatsForSingleItem() {
        //given
        HashMap<String, Integer> statMap = new HashMap<>();
        statMap.put("Strength", 200);
        statMap.put("Dexterity", 400);

        Item testItem = saveItem(statMap);

        //when
        StatBlock result = service.calculateProfileStatBlock(List.of(testItem));
        //then
        assertThat(result).isEqualTo(new StatBlock(200, 400, 0));
    }

    @Test
    public void test_calculateStatBlockForTwoDifferentItems_returnCombinedStatsFromBothMaps() {
        //given
        HashMap<String, Integer> statMapItem1 = new HashMap<>();
        statMapItem1.put("Strength", 200);
        statMapItem1.put("Dexterity", 400);

        HashMap<String, Integer> statMapItem2 = new HashMap<>();
        statMapItem2.put("Dexterity", 150);
        statMapItem2.put("Mind", 10);

        Item testItem1 = saveItem(statMapItem1);
        Item testItem2 = saveItem(statMapItem2);

        //when
        StatBlock result = service.calculateProfileStatBlock(List.of(testItem1, testItem2));
        //then
        assertThat(result).isEqualTo(new StatBlock(200, 550, 10));
    }

    @Test
    public void test_calculateStatBlockForNegativeItem_returnCombinedStatsFromBothMaps() {
        //given
        HashMap<String, Integer> statMapItem1 = new HashMap<>();
        statMapItem1.put("Strength", 200);
        statMapItem1.put("Dexterity", 400);

        HashMap<String, Integer> statMapItem2 = new HashMap<>();
        statMapItem2.put("Dexterity", -150);
        statMapItem2.put("Mind", -10);

        Item testItem1 = saveItem(statMapItem1);
        Item testItem2 = saveItem(statMapItem2);

        //when
        StatBlock result = service.calculateProfileStatBlock(List.of(testItem1, testItem2));
        //then
        assertThat(result).isEqualTo(new StatBlock(200, 250, -10));
    }

    private Item saveItem(Map<String,Integer> statMap) {
        return new Item(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                "Test-Item",
                90,
                400,
                ItemSlot.SECONDARY,
                (HashMap<String, Integer>)statMap);
    }
}
