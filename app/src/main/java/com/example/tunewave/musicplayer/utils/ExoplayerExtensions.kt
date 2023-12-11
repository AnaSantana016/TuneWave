package com.example.tunewave.musicplayer.utils

import com.google.android.exoplayer2.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

fun Player.getCurrentPlaybackProgressFlow() = flow {
    if (!isPlaying) return@flow
    while (currentPosition <= duration) {
        emit(currentPosition)
        delay(1_000)
    }
    emit(currentPosition)
}.distinctUntilChanged()