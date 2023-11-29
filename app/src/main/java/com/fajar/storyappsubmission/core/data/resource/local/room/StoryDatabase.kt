package com.fajar.storyappsubmission.core.data.resource.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponseItems


@Database(
    entities = [StoryResponseItems::class, KeyEntity::class],
    version = 2,
    exportSchema = false
)
abstract class StoryDatabase :RoomDatabase(){
    abstract fun storyDao() : StoryDao
    abstract fun KeysDao(): KeyDao

    companion object {
        @Volatile private var instance: StoryDatabase? = null

        fun getDatabase(context: Context): StoryDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, StoryDatabase::class.java, "story")
                .fallbackToDestructiveMigration()
                .build()
    }
}
