package com.fajar.storyappsubmission.core.data.resource.remote.story

import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDatabase
import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
import com.fajar.storyappsubmission.core.data.resource.remote.home.HomeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorySource @Inject constructor(
    private val homeService: HomeService,
    private val storyServices: StoryServices,
    private val storyDatabase: StoryDatabase
) {

//    fun loadData(token: String): Flow<PagingData<StoryResponseItems>> {
//        @OptIn(ExperimentalPagingApi::class)
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            remoteMediator = RemoteMediator(storyDatabase, home , token),
//            pagingSourceFactory = {
////                StoryPagingSource(storyServices, token)
//                storyDatabase.storyDao().getAllStories()
//            }
//        ).flow
//    }

    suspend fun getData(): List<StoryResponseItems> {
        return storyDatabase.storyDao().getMapAll()
    }

    suspend fun addStory(
//        token: String,
        desc: String,
        img: MultipartBody.Part
    ): Flow<ApiResult<StoryResponse>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = storyServices.addStory(
//                    BearerToken = "Bearer $token",
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


