package com.rcll.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rcll.mainscreen.init.MainScreenInitContent
import com.rcll.mainscreen.ready.content.MainScreenReadyContent

@Composable
fun MainScreenContent(state: MainScreen) {
    Scaffold { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            when (state) {
                MainScreen.Init -> MainScreenInitContent()
                is MainScreen.Ready -> MainScreenReadyContent(state)
            }
        }
    }
}
