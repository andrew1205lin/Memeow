package com.example.memeow.feature_main.data.data_source.local

import androidx.room.*
import com.example.memeow.feature_main.data.data_source.local.entity.MemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {
    @Query("Select * FROM meme")
    fun getMeme(): Flow<List<MemeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeme(meme: MemeEntity)

    @Delete
    suspend fun deleteMeme(meme: MemeEntity)
}