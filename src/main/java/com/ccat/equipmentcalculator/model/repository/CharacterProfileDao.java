package com.ccat.equipmentcalculator.model.repository;


import com.ccat.equipmentcalculator.model.entity.CharacterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterProfileDao extends JpaRepository<CharacterProfile, Long> {

}
