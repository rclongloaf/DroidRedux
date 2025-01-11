package com.rcll.navigation

import androidx.compose.runtime.Composable

@Composable
fun <TKey, TValue> NavigationContent(
    navigation: Navigation<TKey, TValue>,
    content: @Composable (Tab<TKey, TValue>) -> Unit,
) {
    val tab = navigation.tabs.find { it.key == navigation.activeTabKey }
    tab?.let {
        content(tab)
    }
}
