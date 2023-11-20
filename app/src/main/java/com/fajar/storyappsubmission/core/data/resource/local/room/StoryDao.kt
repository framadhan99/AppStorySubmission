package com.fajar.storyappsubmission.core.data.resource.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.features.hometest.StoryResponseItems

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<StoryResponseItems>)

//    @Query("SELECT * FROM Story")
//    fun getAllStory(): PagingSource<Int, Story>
    @Query("SELECT * FROM Story")
    fun getAllStory(): PagingSource<Int, StoryResponseItems>

    @Query("SELECT * FROM Story")
    fun getAllStories(): PagingSource<Int, StoryResponseItems>


    @Query("SELECT * FROM Story")
    suspend fun getMapAll(): List<StoryResponseItems>


    @Query("DELETE FROM Story")
    suspend fun deleteAll()
}