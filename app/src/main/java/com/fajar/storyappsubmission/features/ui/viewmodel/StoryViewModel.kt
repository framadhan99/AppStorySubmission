package com.fajar.storyappsubmission.features.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryRepo
import com.fajar.storyappsubmission.core.data.resource.remote.story.StorySource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(storyRepo: StoryRepo): ViewModel() {
    val story: LiveData<PagingData<Story>> =
        storyRepo.loadData().cachedIn(viewModelScope)
}