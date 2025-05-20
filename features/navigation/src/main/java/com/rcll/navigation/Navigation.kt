package com.rcll.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import kotlinx.collections.immutable.PersistentList

@Stable
interface Navigation<TKey, TValue> {
    val key: NavigationKey
    val tabs: State<PersistentList<Tab<TKey, TValue>>>
    val activeTabKey: State<TKey>
}

@JvmInline
@Stable
value class NavigationKey(val value: Any)

fun Any.asNavigationKey() = NavigationKey(this)

data class Tab<TKey, out TValue>(
    val key: TKey,
    val data: TValue
)

data class MutableNavigation<TKey, TValue>(
    override val key: NavigationKey,
    override val tabs: MutableState<PersistentList<Tab<TKey, TValue>>>,
    override val activeTabKey: MutableState<TKey>
) : Navigation<TKey, TValue>