package com.ptk.testjpctmdb.viewmodel

import androidx.lifecycle.ViewModel
import com.ptk.testjpctmdb.ui.ui_state.FavUIStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
) : ViewModel() {
    private val _uiStates = MutableStateFlow(FavUIStates())
    val uiStates = _uiStates.asStateFlow()
}