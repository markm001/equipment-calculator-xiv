package com.ccat.equipmentcalculator.model

import com.ccat.equipmentcalculator.model.entity.Item
import com.ccat.equipmentcalculator.model.entity.enums.CharacterClass

class GearSetResponse(
        val id: Long,

        val gearClass: CharacterClass,

        val itemLevel: Int,

        val equippedItems: List<Item>


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GearSetResponse

        if (id != other.id) return false
        if (gearClass != other.gearClass) return false
        if (itemLevel != other.itemLevel) return false
        if (equippedItems != other.equippedItems) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + gearClass.hashCode()
        result = 31 * result + itemLevel
        result = 31 * result + equippedItems.hashCode()
        return result
    }

    override fun toString(): String {
        return "GearSetResponse(id=$id, gearClass=$gearClass, itemLevel=$itemLevel, equippedItems=$equippedItems)"
    }

}