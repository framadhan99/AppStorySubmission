package com.fajar.storyappsubmission.features.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fajar.storyappsubmission.core.data.resource.remote.maps.MapsRepository
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class MapsViewModel @Inject constructor(private val mapsRepository: MapsRepository): ViewModel() {
    sealed class MapsResult<out T: Any>{
        data class Success<out T : Any>(val data : T) : MapsResult<T>()
        data class Error(val message : String ): MapsResult<Nothing>()
    }
    val isLoading : LiveData<Boolean>
        get() = mapsRepository.isLoading
    suspend fun getLatLong() : MapsResult<StoryResponse>{
        return when(val result = mapsRepository.getStoryWithCoordinates()){
            is MapsRepository.MapsResponse.Success -> MapsResult.Success(result.data)
            is MapsRepository.MapsResponse.Error -> MapsResult.Error(result.message)
        }
    }
}