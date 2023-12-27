package com.example.cymonbattle.roomDB.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cymonbattle.roomDB.Converters

@Entity(tableName = "pokemon_table")
@TypeConverters(Converters::class)
data class PokemonEntity (
    @PrimaryKey val species: String,
    val nickName: String,
    val level: Int,
    val baseHp: Int,
    val baseAttack: Int,
    val baseDefense: Int,
    val baseSpAttack: Int,
    val baseSpDefense: Int,
    val baseSpeed: Int,
    val frontImgURL: String,
    val backImgURL: String,
    val types: List<String>,
    val currentMoves: List<String>? = null

)
