package com.example.tunewave.viewmodels.homefeedviewmodel.greetingphrasegenerator

import java.time.LocalTime
import javax.inject.Inject

class CurrentTimeBasedGreetingPhraseGenerator @Inject constructor() : GreetingPhraseGenerator {
    override fun generatePhrase(): String =
        TimeBasedGreetingPhraseGenerator(LocalTime.now()).generatePhrase()

}