package com.fajar.storyappsubmission.core.data.resource.remote.story

import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface StoryServices {

//    @GET("stories")
//    suspend fun getStory(
//        @Header("Authorization") BearerToken: String
//    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
//        @Header("Authorization") BearerToken: String,
        @Part("description") desc: RequestBody,
        @Part file: MultipartBody.Part
    ): StoryResponse

    @GET("stories")
    suspend fun getStories(
//        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int? = null
    ): StoryResponse

}