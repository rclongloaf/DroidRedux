package com.rcll.navigation

import kotlinx.collections.immutable.ImmutableList

data class Navigation<TKey, TValue>(
    val tabs: ImmutableList<Tab<TKey, TValue>>,
    val activeTabKey: TKey
)

data class Tab<TKey, TValue>(
    val key: TKey,
    val data: TValue
)
