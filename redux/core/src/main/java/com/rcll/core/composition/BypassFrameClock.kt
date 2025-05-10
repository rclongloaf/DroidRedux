package com.rcll.core.composition

import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.State

/**
 * Синхронная реализация [MonotonicFrameClock], которая позволяет сразу запускать рекомпозицию
 * при уведомлении об изменениях читаемых [State]-ов.
 */
object BypassFrameClock : MonotonicFrameClock {
    override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R {
        return onFrame(System.nanoTime())
    }
}