package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface ItemDao extends JpaRepository<Item, Long> {
    @Query(value = "SELECT i FROM Item i JOIN FETCH i.jobCategories category WHERE category =:category AND level =:level")
    List<Item> findByCategoryAndLevel(ClassJobCategory category, int level);

    @Query(value = "SELECT i FROM Item i WHERE id IN :itemIds")
    List<Item> findByIds(List<Long> itemIds);
}
