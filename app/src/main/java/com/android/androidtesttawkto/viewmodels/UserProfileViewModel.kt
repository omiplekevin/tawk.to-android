package com.android.androidtesttawkto.viewmodels

import androidx.lifecycle.*
import com.android.androidtesttawkto.models.UserProfileModel
import com.android.androidtesttawkto.repository.MainRepository
import com.android.androidtesttawkto.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val safeStateHandle: SavedStateHandle,
    private val repository: MainRepository
) : ViewModel() {

    private val _state: MutableLiveData<DataState<UserProfileModel>> = MutableLiveData()

    val state: LiveData<DataState<UserProfileModel>> get() = _state

    fun setProfileStateEvent(profileStateEvent: ProfileStateEvent, username: String?, id: Int?) {
        viewModelScope.launch {
            when (profileStateEvent) {
                is ProfileStateEvent.GetUserProfile -> {
                    repository.getUserProfile(
                        username ?: "",
                        id ?: 0
                    )
                        .onEach {
                            _state.value = it
                        }
                        .launchIn(viewModelScope)
                }

                is ProfileStateEvent.None -> {

                }
            }
        }
    }

    fun updateProfileStateEvent(profileStateEvent: ProfileStateEvent, userProfileModel: UserProfileModel) {
        viewModelScope.launch {
            when (profileStateEvent) {
                is ProfileStateEvent.UpdateUserProfile -> {
                    repository.saveUserNotes(userProfileModel)
                        .onEach {
                            _state.value = it
                        }
                        .launchIn(viewModelScope)
                }

                is ProfileStateEvent.None -> {

                }
            }
        }
    }
}

sealed class ProfileStateEvent {

    object GetUserProfile : ProfileStateEvent()

    object UpdateUserProfile: ProfileStateEvent()

    object None : ProfileStateEvent()
}