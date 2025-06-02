package com.politecnico.athleticapp.ui.weeklychallenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class WeeklyChallengeViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // In a real app, you would fetch this data from a repository or use case
    // For now, we'll simulate a delay and then show the content.
    fun loadChallengeData() {
        _isLoading.value = true
        // Simulate network/data loading delay
        viewModelScope.launch {
            delay(2000)
            _isLoading.value = false
        }
    }
} 