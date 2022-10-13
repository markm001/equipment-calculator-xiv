package com.ccat.equipmentcalculator.model

import com.ccat.equipmentcalculator.model.entity.StatBlock
import com.ccat.equipmentcalculator.model.entity.enums.CharacterClass

class CharacterProfileResponse(
        val id: Long,
        val characterClass: CharacterClass,
        val gearSetResponse: List<GearSetResponse?>,
        val statBlock: StatBlock
)