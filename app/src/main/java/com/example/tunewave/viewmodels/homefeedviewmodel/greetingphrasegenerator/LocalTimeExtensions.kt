package com.example.tunewave.viewmodels.homefeedviewmodel.greetingphrasegenerator

import java.time.LocalTime

val LocalTime.isMorning get() = this in LocalTime.of(0, 0)..LocalTime.of(11, 59)

val LocalTime.isNoon get() = this in LocalTime.of(12, 0)..LocalTime.of(17, 59)
