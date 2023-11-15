package com.fajar.storyappsubmission.features.hometest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import javax.inject.Inject

class HomeVM @Inject constructor(homeRepository: HomeRepository): ViewModel() {
    val story: LiveData<PagingData<StoryResponseItems>> =
        homeRepository.getStory().cachedIn(viewModelScope)
}