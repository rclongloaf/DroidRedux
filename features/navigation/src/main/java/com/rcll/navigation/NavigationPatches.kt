package com.rcll.navigation

import com.rcll.core.api.IAction

sealed interface NavigationAction<TKey, TValue> : IAction {
    data class AddTab<TKey, TValue>(
        val tab: Tab<TKey, TValue>
    ) : NavigationAction<TKey, TValue>

    data class SelectTab<TKey, TValue>(
        val tabKey: TKey
    ) : NavigationAction<TKey, TValue>
}
