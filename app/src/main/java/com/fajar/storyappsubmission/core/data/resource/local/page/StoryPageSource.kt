package com.fajar.storyappsubmission.core.data.resource.local.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryServices
import com.fajar.storyappsubmission.features.hometest.HomeService
import com.fajar.storyappsubmission.features.hometest.StoryResponseItems

class StoryPageSource(private val homeService: HomeService) : PagingSource<Int, StoryResponseItems>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponseItems> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = homeService.fetchStory(page, params.loadSize).listStory

            LoadResult.Page(
                data = responseData,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryResponseItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}