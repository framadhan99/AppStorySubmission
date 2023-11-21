package com.fajar.storyappsubmission.features.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.fajar.storyappsubmission.core.data.model.User
import com.fajar.storyappsubmission.core.data.resource.local.store.DataStoreManager
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepo,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun tokenUser(): LiveData<String> {
        return dataStoreManager.tokenUser.asLiveData()
    }

    fun pagingStory() = storyRepository.loadData().cachedIn(viewModelScope)


    suspend fun getMapAll() = storyRepository.getData()

    fun removeSession() = viewModelScope.launch {
        dataStoreManager.deleteSession()
    }
}