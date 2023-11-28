package com.fajar.storyappsubmission.features.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fajar.storyappsubmission.core.data.resource.remote.home.HomeRepository
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponseItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class HomeVM @Inject constructor(homeRepository: HomeRepository): ViewModel() {
    val story: LiveData<PagingData<StoryResponseItems>> =
        homeRepository.getStory().cachedIn(viewModelScope)
}