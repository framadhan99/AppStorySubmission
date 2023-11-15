package com.fajar.storyappsubmission.features.hometest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDatabase
import javax.inject.Inject

class HomeRepository @Inject constructor(private val storyDatabase: StoryDatabase, private val homeService: HomeService){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    fun getStory(): LiveData<PagingData<StoryResponseItems>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, homeService),
            pagingSourceFactory = {
                storyDatabase.StoryDao().getAllStory()
            }
        ).liveData
    }



}