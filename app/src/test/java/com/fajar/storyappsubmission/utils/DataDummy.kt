package com.fajar.storyappsubmission.utils

import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryRepo
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponse
import com.fajar.storyappsubmission.features.hometest.StoryResponseItems

object DataDummy {
    fun generateDummyQuoteResponse(): List<StoryResponseItems> {
        val items: MutableList<StoryResponseItems> = arrayListOf()
        for (i in 0..100) {
            val story = StoryResponseItems(
                i.toString(),
                "created at + $i",
                "name $i",
                "description $i",
                i.toDouble(),
                "id : $i",
                i.toDouble(),
            )
            items.add(story)
        }
        return items
    }
}