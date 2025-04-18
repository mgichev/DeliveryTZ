package com.deliverytz.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodeViewModel : ViewModel() {
    private var code = MutableLiveData<String>("1234")
    val _code: LiveData<String>
        get() = code
}