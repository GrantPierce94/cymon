package com.example.cymonbattle.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cymonbattle.roomDB.entity.PlayerEntity

@Dao
interface IPlayerDAO {
    @Query("SELECT * FROM player_table")
    fun getPlayer(): PlayerEntity


    @Insert
    suspend fun insertPlayer(player: PlayerEntity)

    @Query("DELETE FROM player_table")
    fun deletePlayersFromTable()

}

