package com.example.cymonbattle.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cymonbattle.roomDB.entity.PokemonEntity

@Dao
interface IPokemonDAO {
    @Query("SELECT * FROM pokemon_table WHERE species = :species")
    fun getPokemonEntity(species: String): PokemonEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonEntity(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonEntity(pokemonList: List<PokemonEntity>)


}