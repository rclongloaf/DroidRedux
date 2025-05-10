package com.rcll.navigation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import com.rcll.compose.rememberLateinitMutableState
import com.rcll.core.base.LocalStoreDispatcher
import com.rcll.navigation.Navigation
import com.rcll.navigation.NavigationAction
import com.rcll.navigation.Tab
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf

@Composable
fun <TKey, TValue, TValueUI : Any> NavigationUIMapper(
    navigationUIState: MutableState<NavigationUI<TValueUI>>,
    navigation: Navigation<TKey, TValue>,
    activeTabUIMapper: @Composable (MutableState<TValueUI>, Tab<TKey, TValue>) -> Unit,
) {
    val activeTab = navigation.tabs.find { it.key == navigation.activeTabKey }
        ?: error("Active tab not found")

    val tabsUIState = rememberLateinitMutableState<ImmutableList<TabUI>>()
    val activeTabUIState = rememberLateinitMutableState<TValueUI>()

    TabsUIMapper(tabsUIState, navigation)
    activeTabUIMapper(activeTabUIState, activeTab)

    navigationUIState.value = NavigationUI(
        tabs = tabsUIState,
        activeTabUI = activeTabUIState
    )
}

@Composable
@NonRestartableComposable
private fun <TKey, TValue> TabsUIMapper(
    tabsUIState: MutableState<ImmutableList<TabUI>>,
    navigation: Navigation<TKey, TValue>
) {
    val dispatcher = LocalStoreDispatcher.current

    tabsUIState.value = persistentListOf<TabUI>().mutate { mutator ->
        navigation.tabs.forEach { tab ->
            mutator.add(
                TabUI(
                    title = tab.key.toString(), // for example
                    isActive = tab.key == navigation.activeTabKey,
                    onClick = {
                        dispatcher.dispatch(
                            NavigationAction.SelectTab<TKey, TValue>(
                                navigation.key,
                                tab.key
                            )
                        )
                    }
                )
            )
        }
    }
}