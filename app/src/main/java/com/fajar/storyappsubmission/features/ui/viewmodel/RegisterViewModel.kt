package com.fajar.storyappsubmission.features.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthBody
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthRepo
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepo) : ViewModel() {

    fun registerUser(authBody: AuthBody): LiveData<ApiResult<AuthResponse>> {
        val result = MutableLiveData<ApiResult<AuthResponse>>()
        viewModelScope.launch {
            authRepository.registerUser(authBody).collect {
                result.postValue(it)
            }
        }
        return result
    }

}