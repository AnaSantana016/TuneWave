package com.example.tunewave.viewmodels.homefeedviewmodel.greetingphrasegenerator

import java.time.LocalTime

class TimeBasedGreetingPhraseGenerator(
    private val time: LocalTime = LocalTime.now()
) : GreetingPhraseGenerator {
    override fun generatePhrase(): String = when {
        time.isMorning -> "Good morning"
        time.isNoon -> "Good afternoon"
        else -> "Good evening"
    }
}
