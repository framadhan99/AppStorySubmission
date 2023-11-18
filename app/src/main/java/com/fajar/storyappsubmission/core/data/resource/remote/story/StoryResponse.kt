package com.fajar.storyappsubmission.core.data.resource.remote.story

import com.fajar.storyappsubmission.core.data.model.Story
import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @field:SerializedName("listStory")
    val listStory: List<Story>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)