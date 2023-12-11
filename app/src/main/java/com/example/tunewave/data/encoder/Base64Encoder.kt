package com.example.tunewave.data.encoder

fun interface Base64Encoder {
    fun encodeToString(input: ByteArray): String
}