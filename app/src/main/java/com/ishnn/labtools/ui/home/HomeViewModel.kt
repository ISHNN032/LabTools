package com.ishnn.labtools.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "커뮤니티 페이지 제작 예정"
    }
    val text: LiveData<String> = _text
}