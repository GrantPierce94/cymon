package com.example.cymonbattle.roomDB.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cymonbattle.roomDB.Converters

@Entity(tableName = "player_table")
@TypeConverters(Converters::class)
data class PlayerEntity(
    @PrimaryKey val name: String,
    val pokemonTeam:  List<PokemonEntity>,
    val pokemonCollection:  List<PokemonEntity>
)