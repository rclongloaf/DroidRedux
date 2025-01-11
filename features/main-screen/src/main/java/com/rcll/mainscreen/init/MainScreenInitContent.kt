package com.rcll.mainscreen.init;

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Suppress("NOTHING_TO_INLINE")
@Composable
internal fun MainScreenInitContent() {
    MainScreenInitHandler()

    Text(text = "Init state")
}
