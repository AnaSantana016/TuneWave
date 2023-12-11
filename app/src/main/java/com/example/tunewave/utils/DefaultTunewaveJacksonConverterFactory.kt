package com.example.tunewave.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import retrofit2.converter.jackson.JacksonConverterFactory

private val jacksonModule = kotlinModule {
    configure(KotlinFeature.StrictNullChecks, true)
}

val defaultMusifyJacksonConverterFactory: JacksonConverterFactory = JacksonConverterFactory.create(
    jsonMapper { addModule(jacksonModule) }
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
)