package com.fajar.storyappsubmission.core.data.resource.remote.home

import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

interface HomeService {
    @GET("stories")
    suspend fun fetchStory(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

}

