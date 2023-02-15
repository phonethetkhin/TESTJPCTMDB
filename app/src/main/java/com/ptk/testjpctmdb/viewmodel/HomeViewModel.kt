package com.ptk.testjpctmdb.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.testjpctmdb.data.RemoteResource
import com.ptk.testjpctmdb.data.dto.MovieModel
import com.ptk.testjpctmdb.repository.HomeRepository
import com.ptk.testjpctmdb.ui.ui_state.HomeUIStates
import com.ptk.testjpctmdb.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val application: Application
) : ViewModel() {
    private val _uiStates = MutableStateFlow(HomeUIStates())
    val uiStates = _uiStates.asStateFlow()

    fun onSearchValueChange(newValue: String) {
        _uiStates.update { it.copy(searchFieldValue = newValue) }
    }

    fun togglePageChanged(index: Int) {
        _uiStates.update { it.copy(currentPage = index) }
    }

    fun toggleFav(isUpcoming: Boolean, movieModel: MovieModel) {
        if (isUpcoming) {
            _uiStates.update {
                it.copy(upcomingMovies = _uiStates.value.upcomingMovies?.mapIndexed { index, details ->
                    if (_uiStates.value.upcomingMovies?.indexOf(movieModel) == index) details.copy(
                        isFav = !details.isFav
                    )
                    else details
                } as ArrayList<MovieModel>)
            }
        } else {
            _uiStates.update {
                it.copy(recommendedMovies = _uiStates.value.recommendedMovies?.mapIndexed { index, details ->
                    if (_uiStates.value.recommendedMovies?.indexOf(movieModel) == index) details.copy(
                        isFav = !details.isFav
                    )
                    else details
                } as ArrayList<MovieModel>)
            }
        }
    }


    fun getPopularMovie() {
        repository.getPopularMovie().onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading -> {
                    Log.e("success", "loading")
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }
                }
                is RemoteResource.Success -> {
                    Log.e("success", "success")

                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            recommendedMovies = remoteResource.data.results,
                        )
                    }
                }
                is RemoteResource.Failure -> {
                    Log.e("failure", "failure")

                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                        )
                    }
                    application.showToast(remoteResource.errorMessage.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpcomingMovie() {
        repository.getUpcomingMovie().onEach { remoteResource ->
            when (remoteResource) {
                is RemoteResource.Loading -> {
                    Log.e("success", "loading")
                    _uiStates.update {
                        it.copy(showLoadingDialog = true)
                    }
                }
                is RemoteResource.Success -> {
                    Log.e("success", "success")

                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                            upcomingMovies = remoteResource.data.results,
                        )
                    }
                }
                is RemoteResource.Failure -> {
                    Log.e("failure", "failure")

                    _uiStates.update {
                        it.copy(
                            showLoadingDialog = false,
                        )
                    }
                    application.showToast(remoteResource.errorMessage.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

}