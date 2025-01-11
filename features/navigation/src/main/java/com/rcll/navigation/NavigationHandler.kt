package com.rcll.navigation

import androidx.compose.runtime.Composable

@Composable
fun <TKey, TValue> NavigationHandler(
    navigation: Navigation<TKey, TValue>,
    tabHandler: @Composable (Tab<TKey, TValue>) -> Unit
) {
    for (tab in navigation.tabs) {
        tabHandler(tab)
    }
}
