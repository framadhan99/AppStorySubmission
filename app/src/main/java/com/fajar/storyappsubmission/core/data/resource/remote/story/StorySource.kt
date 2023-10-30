package com.fajar.storyappsubmission.core.data.resource.remote.story

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.local.RemoteMediator
import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDatabase
import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorySource @Inject constructor(
    private val storyServices: StoryServices,
    private val storyDatabase: StoryDatabase
) {

    fun loadData(token: String): Flow<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = RemoteMediator(storyDatabase, storyServices, token),
            pagingSourceFactory = {
//                StoryPagingSource(storyServices, token)
                storyDatabase.StoryDao().getAllStory()
            }
        ).flow
    }

    suspend fun getData(): List<Story> {
        return storyDatabase.StoryDao().getMapAll()
    }

    suspend fun addStory(
        token: String,
        desc: String,
        img: MultipartBody.Part
    ): Flow<ApiResult<StoryResponse>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = storyServices.addStory(
                    BearerToken = "Bearer $token",
                    desc.toRequestBody("text/plain".toMediaType()),
                    img
//                    desc.toRequestBody("text/plain".toMediaType()),
//                    img.asRequestBody()
                )
                if (!response.error) {
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }
}


