package com.fajar.storyappsubmission.core.data.resource.remote.home

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDatabase
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryRemoteMediator
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponseItems
import javax.inject.Inject

class HomeRepository @Inject constructor(private val storyDatabase: StoryDatabase, private val homeService: HomeService){

    fun getStory(): LiveData<PagingData<StoryResponseItems>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, homeService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }





}