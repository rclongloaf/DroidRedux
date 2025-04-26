package com.rcll.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key

@Composable
fun <TKey, TValue> NavigationFeature(
    navigation: Navigation<TKey, TValue>,
    tabFeature: @Composable (Tab<TKey, TValue>) -> Unit
) {
    for (tab in navigation.tabs) {
        key(tab.key) { tabFeature(tab) }
    }
}
