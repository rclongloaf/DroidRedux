package com.rcll.core.ui

import androidx.compose.runtime.State

interface UIStateProvider<T> {
    val uiState: State<T>
}