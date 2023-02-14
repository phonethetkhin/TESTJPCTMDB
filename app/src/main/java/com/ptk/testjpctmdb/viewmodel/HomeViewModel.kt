package com.ptk.testjpctmdb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.ptk.testjpctmdb.ui.ui_state.HomeUIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val repository: FilterRepository,
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
}