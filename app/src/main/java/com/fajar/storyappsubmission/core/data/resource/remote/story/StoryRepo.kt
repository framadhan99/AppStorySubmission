package com.fajar.storyappsubmission.core.data.resource.remote.story

import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
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