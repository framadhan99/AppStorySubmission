package com.fajar.storyappsubmission.features.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fajar.storyappsubmission.core.data.resource.local.store.DataStoreManager
import com.fajar.storyappsubmission.core.data.resource.remote.ApiResult
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthBody
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthRepo
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepo,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    val tokenUser: LiveData<String> = dataStoreManager.tokenUser.asLiveData()

    fun loginUser(authBody: AuthBody): LiveData<ApiResult<AuthResponse>> {
        val result = MutableLiveData<ApiResult<AuthResponse>>()
        viewModelScope.launch {
            authRepository.loginUser(authBody).collect {
                result.postValue(it)
            }
        }
        return result
    }


}