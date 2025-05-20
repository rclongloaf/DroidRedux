package com.rcll.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf

interface NavigationReducer<TKey, TValue> : Reducer<MutableNavigation<TKey, TValue>>

open class NavigationReducerImpl<TKey, TValue : Any>(
    private val tabReducer: Reducer<TValue>,
) : NavigationReducer<TKey, TValue> {
    @Suppress("UNCHECKED_CAST")
    override fun reduce(
        state: MutableNavigation<TKey, TValue>,
        action: Action
    ): MutableNavigation<TKey, TValue> {
        var tabs by state.tabs
        tabs = tabs.mutate { mutableTabs ->
            tabs.forEachIndexed { index, tab ->
                val newData = tabReducer.reduce(tab.data, action)

                if (newData != tab.data) {
                    mutableTabs[index] = Tab(tab.key, newData)
                }
            }
        }

        // Нормально привести к типу дженерика можно только через as?
        val navigationAction =  (action as? NavigationAction<TKey, TValue>) ?: return state

        if (navigationAction.navigationKey != state.key) return state

        return when (navigationAction) {
            is NavigationAction.AddTab<TKey, TValue> -> applyAddTab(state, navigationAction)
            is NavigationAction.SelectTab<TKey, TValue> -> applySelectTab(
                state,
                navigationAction
            )
        }
    }

    private fun <TKey, TValue> applyAddTab(
        navigation: MutableNavigation<TKey, TValue>,
        action: NavigationAction.AddTab<TKey, TValue>
    ): MutableNavigation<TKey, TValue> {
        var tabs by navigation.tabs

        tabs = tabs.add(action.tab)

        return navigation
    }

    private fun <TKey, TValue> applySelectTab(
        navigation: MutableNavigation<TKey, TValue>,
        action: NavigationAction.SelectTab<TKey, TValue>
    ): MutableNavigation<TKey, TValue> {
        var activeTabKey by navigation.activeTabKey

        activeTabKey = action.tabKey

        return navigation
    }

}