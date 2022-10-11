package com.ccat.equipmentcalculator.model.repository;

import com.ccat.equipmentcalculator.model.entity.GearSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GearSetDao extends JpaRepository<GearSet, Long> {
    @Query(value = "SELECT s FROM GearSet s WHERE profileId =:profileId")
    List<GearSet> findByProfileId(Long profileId);
}