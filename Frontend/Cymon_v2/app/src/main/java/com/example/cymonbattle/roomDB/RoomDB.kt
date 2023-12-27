package com.example.cymonbattle.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cymonbattle.roomDB.dao.IMoveDAO
import com.example.cymonbattle.roomDB.dao.IPlayerDAO
import com.example.cymonbattle.roomDB.dao.IPokemonDAO
import com.example.cymonbattle.roomDB.entity.MoveEntity
import com.example.cymonbattle.roomDB.entity.PlayerEntity
import com.example.cymonbattle.roomDB.entity.PokemonEntity


@Database(entities = [PokemonEntity::class, MoveEntity::class, PlayerEntity::class],
        version = 1, exportSchema = false)
abstract class RoomDB: RoomDatabase(){
        abstract fun pokemonDAO(): IPokemonDAO
        abstract fun moveDAO(): IMoveDAO
        abstract fun playerDAO(): IPlayerDAO

        companion object {
                @Volatile
                private var INSTANCE: RoomDB? = null

                fun getDatabase(context: Context): RoomDB {
                        // if the INSTANCE is not null, then return it,
                        // if it is, then create the database
                        return INSTANCE ?: synchronized(this) {
                                val instance = Room.databaseBuilder(
                                        context.applicationContext,
                                        RoomDB::class.java,
                                        "battle_database"
                                ).build()
                                INSTANCE = instance
                                // return instance
                                instance
                        }
                }
        }
}