package com.fajar.storyappsubmission.utils

import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryRepo
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponse

object DataDummy {


    fun generateDummyQuoteResponse(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                id = "id",
                name = "name",
                description = "description",
                photoUrl = "photoUrl",
                createdAt = "createdAt",
                lat = 0.01,
                lon = 0.01
            )
        }
        return items
    }

}