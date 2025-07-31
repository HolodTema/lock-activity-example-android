package com.terabyte.lockscreenexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val liveDataPassword = MutableLiveData("")
}