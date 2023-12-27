package com.example.cymonbattle.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cymonbattle.roomDB.entity.MoveEntity

@Dao
interface IMoveDAO {
    @Query("SELECT * FROM move_table WHERE name = :move")
    fun getMoveEntity(move: String): MoveEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMoveEntity(move: MoveEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMoveEntity(moveList: List<MoveEntity>)
}