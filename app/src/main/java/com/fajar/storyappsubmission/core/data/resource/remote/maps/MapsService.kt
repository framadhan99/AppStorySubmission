package com.fajar.storyappsubmission.core.data.resource.remote.maps

import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsService {
    @GET("stories")
    suspend fun fetchStoryWithCoordinates(@Query("location")location : String) : StoryResponse
}