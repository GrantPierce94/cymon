package com.example.cymonbattle.roomDB

import androidx.room.TypeConverter
import com.example.cymonbattle.roomDB.entity.PokemonEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Converters for Room Database
 */
class Converters {

    @TypeConverter
    fun fromPokemonEntityList(value: List<PokemonEntity>): String{
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toPokemonEntityList(value: String): List<PokemonEntity>{
        val moveToken = object: TypeToken<List<PokemonEntity>>() {}.type
        return Gson().fromJson(value, moveToken)
    }

    /**
     * This method is to convert an List<String> to a String
     */
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    /**
     * This method is to convert a String to a List<String>
     */
    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value, List::class.java)
        } catch (e: Exception) {
            arrayListOf()
        }
    }



}