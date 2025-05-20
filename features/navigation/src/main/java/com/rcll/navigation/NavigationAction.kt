package com.rcll.navigation

import com.rcll.core.api.Action

sealed interface NavigationAction<TKey, TValue> : Action {
    val navigationKey: NavigationKey

    data class AddTab<TKey, TValue>(
        override val navigationKey: NavigationKey,
        val tab: Tab<TKey, TValue>
    ) : NavigationAction<TKey, TValue>

    data class SelectTab<TKey, TValue>(
        override val navigationKey: NavigationKey,
        val tabKey: TKey
    ) : NavigationAction<TKey, TValue>
}
