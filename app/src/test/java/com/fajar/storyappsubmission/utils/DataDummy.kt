package com.fajar.storyappsubmission.utils

import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryRepo
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponse

object DataDummy {


    fun generateDummyQuoteResponse(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                "$i",
                "name $i",
                "description $i",
                "$i",
                "id : $i",
                i.toDouble(),
                i.toDouble(),
            )
            items.add(story)
        }
        return items
    }

}