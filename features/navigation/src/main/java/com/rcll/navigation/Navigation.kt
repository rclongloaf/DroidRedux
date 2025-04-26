package com.rcll.navigation

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.PersistentList

data class Navigation<TKey, TValue>(
    val key: NavigationKey,
    val tabs: PersistentList<Tab<TKey, TValue>>,
    val activeTabKey: TKey
) {
    fun smartCopy(
        key: NavigationKey,
        tabs: PersistentList<Tab<TKey, TValue>>,
        activeTabKey: TKey
    ): Navigation<TKey, TValue> {
        if (
            this.key != key ||
            this.tabs !== tabs ||
            this.activeTabKey != activeTabKey
        ) {
            return Navigation(
                key,
                tabs,
                activeTabKey
            )
        }
        return this
    }
}


@JvmInline
@Stable
value class NavigationKey(val value: Any)

fun Any.asNavigationKey() = NavigationKey(this)

data class Tab<TKey, TValue>(
    val key: TKey,
    val data: TValue
)
