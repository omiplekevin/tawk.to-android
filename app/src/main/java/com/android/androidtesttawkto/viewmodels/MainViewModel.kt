package com.android.androidtesttawkto.viewmodels

import androidx.lifecycle.*
import com.android.androidtesttawkto.models.UserModel
import com.android.androidtesttawkto.repository.MainRepository
import com.android.androidtesttawkto.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val safeStateHandle: SavedStateHandle,
    private val repository: MainRepository
): ViewModel() {

    private val _state: MutableLiveData<DataState<List<UserModel>>> = MutableLiveData()

    val state: LiveData<DataState<List<UserModel>>> get() = _state

    fun setStateEvent(mainStateEvent: MainStateEvent, startId: Int, offset: Int, loadMore: Boolean) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetUsersEvent -> {
                    repository.getUsers(startId, offset, loadMore)
                        .onEach {
                            _state.value = it
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.None -> {

                }
            }
        }
    }

    fun searchStateEvent(mainStateEvent: MainStateEvent, keyword: String?) {
        viewModelScope.launch {
            when(mainStateEvent) {

                is MainStateEvent.SearchUsersEvent -> {
                    if (keyword != null && keyword.isNotEmpty()) {
                        repository.searchUserDetail(keyword)
                            .onEach {
                                _state.value = it
                            }
                            .launchIn(viewModelScope)
                    } else {
                        _state.value = null
                    }
                }

                is MainStateEvent.None -> {

                }
            }
        }
    }
}

sealed class MainStateEvent {

    object GetUsersEvent: MainStateEvent()

    object SearchUsersEvent: MainStateEvent()

    object None: MainStateEvent()
}