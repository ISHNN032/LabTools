package com.ishnn.labtools.ui.stopwatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StopwatchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "00:00.00"
    }
    val text: LiveData<String> = _text
}