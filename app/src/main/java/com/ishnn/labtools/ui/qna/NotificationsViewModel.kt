package com.ishnn.labtools.ui.qna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QnAViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is qna Fragment"
    }
    val text: LiveData<String> = _text
}