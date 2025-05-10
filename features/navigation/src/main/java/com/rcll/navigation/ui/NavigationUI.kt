package com.rcll.navigation.ui

import androidx.compose.runtime.State
import kotlinx.collections.immutable.ImmutableList

data class NavigationUI<TValue>(
    val tabs: State<ImmutableList<TabUI>>,
    val activeTabUI: State<TValue>
)

data class TabUI(
    val title: String,
    val isActive: Boolean,
    val onClick: () -> Unit
)