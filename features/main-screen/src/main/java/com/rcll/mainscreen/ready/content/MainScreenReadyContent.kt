package com.rcll.mainscreen.ready.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rcll.mainscreen.ui.MainScreenUI

@Composable
internal fun MainScreenReadyContent(mainScreen: MainScreenUI.Ready) {
    val navigation = mainScreen.navigationUI.value

    Column {
        Text(text = "Ready state")
        Text(text = "${mainScreen.counter}")

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            navigation.tabs.value.forEach { tab ->
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = tab.onClick)
                        .then(if (tab.isActive) Modifier.background(Color.Green) else Modifier),
                    text = tab.title
                )
            }
        }

        Text(text = navigation.activeTabUI.value.title)
    }
}