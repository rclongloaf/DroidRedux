package com.rcll.navigation

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf

interface NavigationReducer<TKey, TValue> : Reducer<Navigation<TKey, TValue>>

open class NavigationReducerImpl<TKey, TValue : Any>(
    private val tabReducer: Reducer<TValue>,
) : NavigationReducer<TKey, TValue> {
    @Suppress("UNCHECKED_CAST")
    override fun reduce(
        state: Navigation<TKey, TValue>,
        action: Action
    ): Navigation<TKey, TValue> {
        val newTabs = state.tabs.mutate { mutableTabs ->
            state.tabs.forEachIndexed { index, tab ->
                val newValue = tabReducer.reduce(tab.data, action)

                if (newValue != tab.data) {
                    mutableTabs[index] = Tab(tab.key, newValue)
                }
            }
        }

        val newState = state.smartCopy(
            key = state.key,
            tabs = newTabs,
            activeTabKey = state.activeTabKey
        )

        (action as? NavigationAction<TKey, TValue>)?.let { navigationAction ->
            return when (navigationAction) {
                is NavigationAction.AddTab<TKey, TValue> -> applyAddTab(newState, navigationAction)
                is NavigationAction.SelectTab<TKey, TValue> -> applySelectTab(
                    newState,
                    navigationAction
                )
            }
        }

        return newState
    }

    private fun <TKey, TValue> applyAddTab(
        navigation: Navigation<TKey, TValue>,
        action: NavigationAction.AddTab<TKey, TValue>
    ): Navigation<TKey, TValue> {
        if (navigation.key != action.navigationKey) return navigation

        return navigation.copy(
            tabs = persistentListOf(action.tab).addAll(navigation.tabs)
        )
    }

    private fun <TKey, TValue> applySelectTab(
        navigation: Navigation<TKey, TValue>,
        action: NavigationAction.SelectTab<TKey, TValue>
    ): Navigation<TKey, TValue> {
        if (navigation.key != action.navigationKey) return navigation

        return navigation.copy(
            activeTabKey = action.tabKey
        )
    }

}