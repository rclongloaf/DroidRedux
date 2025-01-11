package com.rcll.mainscreen.ready.content

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.rcll.core.base.LocalStore
import com.rcll.mainscreen.MainScreen
import com.rcll.navigation.NavigationContent
import com.rcll.navigation.SelectTab

@Suppress("NOTHING_TO_INLINE")
@Composable
internal inline fun MainScreenReadyContent(state: MainScreen.Ready) {
    MainScreenReadyContentHandler(state)

    Column {
        Text(text = "Ready state")
        Text(text = "${state.counter}")

        val navigation = state.navigation

        NavigationContent(navigation) { tab ->
            val store = LocalStore.current

            // handle only current tab state
            Text(tab.data)
            Button(
                onClick = {
                    //todo dispatch click action
                    val notSelectedTabs = navigation.tabs.filter { tab -> tab.key != navigation.activeTabKey }
                    val randomTabKey = notSelectedTabs[System.currentTimeMillis().toInt() % notSelectedTabs.size].key
                    store.dispatch(SelectTab(randomTabKey))
                }
            ) {
                Text(text = "Select random tab")
            }
        }
    }
}
