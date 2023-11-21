package com.fajar.storyappsubmission.core.data.resource.remote.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepo @Inject constructor(private val storySource: StorySource) {

    fun loadData(): LiveData<PagingData<Story>> =
        storySource.loadData().asLiveData()

    suspend fun getData(): List<Story> {
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