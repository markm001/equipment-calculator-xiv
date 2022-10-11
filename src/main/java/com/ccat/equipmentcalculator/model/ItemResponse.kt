package com.ccat.equipmentcalculator.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ItemResponse(

    @JsonProperty("ID")
    val id: Long,

    @JsonProperty("Name")
    val name: String,

    @JsonProperty("LevelEquip")
    val levelEquip: Int,

    @JsonProperty("LevelItem")
    val levelItem: Int,

    @JsonProperty("EquipSlotCategory")
    val equipSlotCategory: Map<String, Int>,

    @JsonProperty("ClassJobCategory")
    val classJobCategory: Map<String, String>,

    @JsonProperty("Stats")
    val stats: Map<String, Map<String, Int>>
)