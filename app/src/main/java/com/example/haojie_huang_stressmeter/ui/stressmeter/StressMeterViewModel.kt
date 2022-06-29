package com.example.haojie_huang_stressmeter.ui.stressmeter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StressMeterViewModel : ViewModel()
{
    var isStopThread = MutableLiveData<Boolean>().apply {
        value = false
    }
}