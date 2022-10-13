package com.ccat.equipmentcalculator.repository;

import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;
import com.ccat.equipmentcalculator.model.repository.ItemDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Deprecated
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:application-test.properties")
public class ItemRepositoryTest {
    @Autowired
    private ItemDao itemDao;

    @Test
    public void test_findByCategoryAndLevel_returnListOfItemsWithLevelAndCategory() {
        //given
        ClassJobCategory requestedCategory = ClassJobCategory.BLU;
        int requestedLevel = 70;
        Item item1 = generateRandomItem(Set.of(requestedCategory, ClassJobCategory.RDM), requestedLevel);
        Item item2 = generateRandomItem(Set.of(requestedCategory, ClassJobCategory.RDM), requestedLevel);
        Item item3 = generateRandomItem(Set.of(requestedCategory, ClassJobCategory.RDM), 90);
        Item item4 = generateRandomItem(Set.of(ClassJobCategory.RDM), 90);

        itemDao.saveAll(List.of(item1,item2,item3,item4));
        //when
        List<Item> itemResponseList = itemDao.findByCategoryAndLevel(requestedCategory,requestedLevel);
        //then
        assertThat(itemResponseList.size()).isEqualTo(2);
        assertThat(itemResponseList).containsExactlyInAnyOrderElementsOf(List.of(item1, item2));
    }

    @Test
    public void test_findItemsByIds_returnRequestedListOfItems() {
        //given
        Item item1 = generateRandomItem(Set.of(ClassJobCategory.RDM), 90);
        Item item2 = generateRandomItem(Set.of(ClassJobCategory.RDM), 90);
        Item item3 = generateRandomItem(Set.of(ClassJobCategory.RDM), 90);
        itemDao.saveAll(List.of(item1, item2, item3));

        List<Long> idsToFind = List.of(item2.getId(), item3.getId());

        //when
        List<Item> itemResponses = itemDao.findByIds(idsToFind);
        //then
        assertThat(itemResponses.size()).isEqualTo(2);
        assertThat(itemResponses).containsExactlyInAnyOrderElementsOf(List.of(item2, item3));
    }

    public Item generateRandomItem(Set<ClassJobCategory> jobCategories, int level) {
        return new Item(
                UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE,
                "Item_Test",
                level,
                250,
                ItemSlot.PRIMARY,
                jobCategories,
                new HashMap<>());
    }
}
