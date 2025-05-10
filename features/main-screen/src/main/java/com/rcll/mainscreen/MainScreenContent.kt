package com.rcll.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.rcll.mainscreen.init.MainScreenInitContent
import com.rcll.mainscreen.ready.content.MainScreenReadyContent
import com.rcll.mainscreen.ui.MainScreenUI

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
