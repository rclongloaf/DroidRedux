package com.rcll.navigation

import com.rcll.core.api.IPatch

sealed interface NavigationPatch: IPatch

data class AddTab<TKey, TValue>(
    val tab: Tab<TKey, TValue>
) : NavigationPatch {
    @Suppress("UNCHECKED_CAST")
    inline fun <reified K, reified V> unsafeCast() : AddTab<K, V> {
        return this as AddTab<K, V>
    }
}

data class SelectTab<TKey>(
    val tabKey: TKey
) : NavigationPatch {
    @Suppress("UNCHECKED_CAST")
    inline fun <reified TKey> unsafeCast() : SelectTab<TKey> {
        return this as SelectTab<TKey>
    }
}
