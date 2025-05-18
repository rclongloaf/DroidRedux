package com.rcll.mainscreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.rcll.mainscreen.init.MainScreenInitContent
import com.rcll.mainscreen.ready.content.MainScreenReadyContent

@Composable
fun MainScreenContent(state: State<MainScreenUI>) {
    Scaffold { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            when (val mainScreenUI = state.value) {
                MainScreenUI.Init -> MainScreenInitContent()
                is MainScreenUI.Ready -> MainScreenReadyContent(mainScreenUI)
            }
        }
    }
}
