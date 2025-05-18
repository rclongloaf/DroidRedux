package com.rcll.navigation

import com.rcll.core.api.Action

sealed interface NavigationAction<TKey, TValue> : Action {
    data class AddTab<TKey, TValue>(
        val navigationKey: NavigationKey,
        val tab: Tab<TKey, TValue>
    ) : NavigationAction<TKey, TValue>

    data class SelectTab<TKey, TValue>(
        val navigationKey: NavigationKey,
        val tabKey: TKey
    ) : NavigationAction<TKey, TValue>
}
