package com.fajar.storyappsubmission.core.data.resource.remote.story

import com.fajar.storyappsubmission.core.data.model.Story

data class StoryResponse(
    val listStory: List<Story>,
    val error: Boolean,
    val message: String
)