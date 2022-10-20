package com.example.demoaerisproject.data.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MyPlaceDao {
    @Query("SELECT * from my_place_table")
    fun getAllMyPlaces(): Flow<List<MyPlace>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(myPlace: MyPlace)

    @Query("DELETE FROM my_place_table")
    suspend fun deleteAll()

    @Query("DELETE FROM my_place_table WHERE name = :selected")
    suspend fun deleteByName(selected: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(myPlace: MyPlace)

    @Query("UPDATE my_place_table SET myLoc = 0")
    suspend fun resetMyLocFalse()
}