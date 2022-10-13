package com.ccat.equipmentcalculator.service;

import com.ccat.equipmentcalculator.client.XivApiClient;
import com.ccat.equipmentcalculator.client.XivApiMockClient;
import com.ccat.equipmentcalculator.exception.InvalidItemSlotException;
import com.ccat.equipmentcalculator.exception.InvalidJobClassException;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.entity.GearItems;
import com.ccat.equipmentcalculator.model.entity.GearSet;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.CharacterClass;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import com.ccat.equipmentcalculator.model.service.GearSetService;
import com.ccat.equipmentcalculator.model.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GearSetServiceTest {
    private GearSetService service;
    private GearSetDao gearSetDao;
    private ItemService itemService;
    private XivApiMockClient xivClient;

    @BeforeEach
    public void init() {
        this.gearSetDao = mock(GearSetDao.class);
        this.xivClient = new XivApiMockClient();
        this.itemService = new ItemService(xivClient);
        this.service = new GearSetService(gearSetDao, itemService);
    }

    @Test
    public void test_updateGearSet_returnGearSetResponse() {
        //given
        CharacterClass characterClass = CharacterClass.PALADIN;
        GearSet emptyGearSet = generateGearSet(characterClass);
        Item item = generateItem(ItemSlot.PRIMARY,Set.of(ClassJobCategory.PLD));
        GearItems e1 = generateGearItemsFromItem(ItemSlot.PRIMARY, item);
        List<GearItems> newGearItemList = List.of(e1);

        given(gearSetDao.findById(emptyGearSet.getId())).willReturn(Optional.of(emptyGearSet));
        xivClient.saveItems(List.of(item));

        int itemLevel = item.getItemLevel() / ItemSlot.values().length;

        GearSetResponse controlResponse = new GearSetResponse(
                emptyGearSet.getId(),
                emptyGearSet.getGearClass(),
                itemLevel,
                List.of(item));
        //when
        GearSetResponse setResponse = service.updateGearSetEquipment(emptyGearSet.getId(), newGearItemList);

        //then
        assertThat(setResponse).isEqualTo(controlResponse);
    }

    @Test
    public void test_updateGearSetWithIncorrectItemSlot_throwsInvalidItemSlotException() {
        //given
        ItemSlot slotToEquipItem = ItemSlot.SECONDARY;

        CharacterClass characterClass = CharacterClass.PALADIN;
        GearSet emptyGearSet = generateGearSet(characterClass);
        Item item = generateItem(ItemSlot.PRIMARY,Set.of(ClassJobCategory.PLD));
        GearItems e1 = generateGearItemsFromItem(slotToEquipItem, item);
        List<GearItems> newGearItemList = List.of(e1);

        given(gearSetDao.findById(emptyGearSet.getId())).willReturn(Optional.of(emptyGearSet));
        xivClient.saveItems(List.of(item));

        //then
        assertThrows(InvalidItemSlotException.class, () -> {
            //when
            service.updateGearSetEquipment(emptyGearSet.getId(), newGearItemList);
        });
    }

    @Test
    public void test_updateGearSetWithMultipleItemsAndOneIncorrectItemJobClass_throwsInvalidJobClassException() {
        //given
        ClassJobCategory itemClassJobCategory = ClassJobCategory.RDM;
        CharacterClass gearSetJobCategory = CharacterClass.PALADIN;

        GearSet emptyGearSet = generateGearSet(gearSetJobCategory);
        Item item = generateItem(ItemSlot.PRIMARY, Set.of(ClassJobCategory.PLD));
        Item item2 = generateItem(ItemSlot.SECONDARY, Set.of(itemClassJobCategory));

        GearItems e1 = generateGearItemsFromItem(ItemSlot.PRIMARY, item);
        GearItems e2 = generateGearItemsFromItem(ItemSlot.SECONDARY, item2);

        List<GearItems> newGearItemList = List.of(e1,e2);
        List<Long> itemIds = List.of(item.getId(), item2.getId());

        given(gearSetDao.findById(emptyGearSet.getId())).willReturn(Optional.of(emptyGearSet));
//        given(xivClient.getItemsByCategoryAndLevel(gearSetJobCategory.getAbbreviation(),90)).willReturn(List.of(item, item2));
        xivClient.saveItems(List.of(item, item2));

        //then
        assertThrows(InvalidJobClassException.class, () -> {
            //when
            service.updateGearSetEquipment(emptyGearSet.getId(), newGearItemList);
        });
    }

    @Test
    public void test_createSlotItemMapForNonPaladinClass_returnSlotItemMapWithoutSecondary() {
        //given
        GearSet warriorSet = generateGearSet(CharacterClass.WARRIOR);
        GearSet paladinSet = generateGearSet(CharacterClass.PALADIN);
        Item randItem = generateItem(ItemSlot.PRIMARY, Set.of(ClassJobCategory.WAR, ClassJobCategory.PLD));
        given(gearSetDao.findById(warriorSet.getId())).willReturn(Optional.of(warriorSet));
        given(gearSetDao.findById(paladinSet.getId())).willReturn(Optional.of(paladinSet));
        //when
        Map<ItemSlot, Item> slotMapForWarrior = service.createSlotItemMap(warriorSet, List.of(randItem));
        Map<ItemSlot, Item> slotMapForPaladin = service.createSlotItemMap(paladinSet, List.of(randItem));

        //then
        assertThat(slotMapForWarrior.containsKey(ItemSlot.SECONDARY)).isFalse();
        assertThat(slotMapForPaladin.containsKey(ItemSlot.SECONDARY)).isTrue();
    }

    private GearItems generateGearItemsFromItem(ItemSlot secondary, Item item2) {
        return new GearItems(
                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                secondary,
                item2.getId());
    }

    private Item generateItem(ItemSlot itemSlot, Set<ClassJobCategory> jobClass) {
        return new Item(
                UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                "R_N",
                90,
                400,
                itemSlot,
                jobClass,
                new HashMap<>());
    }

    private GearSet generateGearSet(CharacterClass characterClass) {
        return new GearSet(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                90,
                characterClass,
                List.of(new GearItems()));
    }
}
