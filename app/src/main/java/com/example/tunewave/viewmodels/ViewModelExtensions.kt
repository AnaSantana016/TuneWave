package com.example.tunewave.viewmodels

import androidx.lifecycle.AndroidViewModel
import com.example.tunewave.di.MusifyApplication

fun AndroidViewModel.getCountryCode(): String = this.getApplication<MusifyApplication>()
    .resources
    .configuration
    .locale
    .country