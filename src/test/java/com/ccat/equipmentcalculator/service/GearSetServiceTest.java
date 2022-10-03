package com.ccat.equipmentcalculator.service;

import com.ccat.equipmentcalculator.exception.InvalidItemSlotException;
import com.ccat.equipmentcalculator.model.GearSetResponse;
import com.ccat.equipmentcalculator.model.entity.*;
import com.ccat.equipmentcalculator.model.repository.GearSetDao;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import com.ccat.equipmentcalculator.model.service.GearSetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GearSetServiceTest {
    private GearSetService service;
    private GearSetDao gearSetDao;
    private ItemDao itemDao;

    @BeforeEach
    public void init() {
        this.itemDao = mock(ItemDao.class);
        this.gearSetDao = mock(GearSetDao.class);
        this.service = new GearSetService(
                gearSetDao,
                itemDao
        );
    }

    @Test
    public void test_updateGearSetWithIncorrectItemSlotItem_throwInvalidItemSlotException() {
        //given
        Item primaryItem = getTestItem(ItemSlot.PRIMARY);
        Item secondaryItem = getTestItem(ItemSlot.SECONDARY);

        EquipmentList equipmentList = new EquipmentList(
                secondaryItem.getId(),
                primaryItem.getId());
        GearSet gearSet = getGearSet(equipmentList);

        given(itemDao.findById(primaryItem.getId())).willReturn(Optional.of(primaryItem));
        given(itemDao.findById(secondaryItem.getId())).willReturn(Optional.of(secondaryItem));
        given(gearSetDao.findById(gearSet.getId())).willReturn(Optional.of(gearSet));

        //then
        assertThrows(InvalidItemSlotException.class, () -> {
            //when
            service.updateGearSetEquipment(gearSet.getId(),equipmentList);
        });
    }

    private Item getTestItem(ItemSlot itemSlot) {
        return new Item(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                "TestItem",
                90,
                400,
                itemSlot,
                new HashMap<>());
    }

    private GearSet getGearSet(EquipmentList equipmentList) {
        return new GearSet(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                CharacterClass.PALADIN,
                400,
                equipmentList);
    }
}
