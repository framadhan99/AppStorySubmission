package com.fajar.storyappsubmission.core.data.resource.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fajar.storyappsubmission.core.data.model.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<Story>)

    @Query("SELECT * FROM Story")
    fun getAllStory(): PagingSource<Int, Story>


    @Query("SELECT * FROM Story")
    suspend fun getMapAll(): List<Story>


    @Query("DELETE FROM Story")
    suspend fun deleteAll()
}