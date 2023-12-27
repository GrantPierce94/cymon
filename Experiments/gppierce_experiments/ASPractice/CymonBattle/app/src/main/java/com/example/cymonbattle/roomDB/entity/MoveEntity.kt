package com.example.cymonbattle.roomDB.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "move_table")
data class MoveEntity (
    @PrimaryKey val name: String,
    val levelReq: Int,
    val category: String,
    val damageClass: String,
    val target: String,
    val type: String,
    val accuracy: Int?,
    val heal: Int,
    val power: Int?,
    val pp: Int,
    val aliment: String,
    val alimentChance: Int
)