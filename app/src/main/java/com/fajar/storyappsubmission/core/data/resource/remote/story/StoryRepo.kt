package com.fajar.storyappsubmission.core.data.resource.remote.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
import com.fajar.storyappsubmission.features.hometest.StoryResponseItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepo @Inject constructor(private val storySource: StorySource) {

//    fun loadData(token: String): LiveData<PagingData<StoryResponseItems>> =
//        storySource.loadData(token).asLiveData()

    suspend fun getData(): List<StoryResponseItems> {
        return storySource.getData()
    }


    suspend fun addStory(
//        token: String,
        desc: String,
        img: MultipartBody.Part
    ): Flow<ApiResult<StoryResponse>> {
        return storySource.addStory( desc, img).flowOn(Dispatchers.IO)
    }


}