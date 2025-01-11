package com.rcll.navigation

import com.rcll.core.api.IPatch
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

inline fun <reified TKey, reified TValue> reduceNavigation(
    navigation: Navigation<TKey, TValue>,
    patch: IPatch,
    crossinline tabsReducers: (TValue, IPatch) -> TValue,
) : Navigation<TKey, TValue> {
    var tabs = navigation.tabs

    if (patch is NavigationPatch) { //todo compare to navigation key
        return when (patch) {
            is AddTab<*, *> -> applyAddTab(navigation, patch.unsafeCast())
            is SelectTab<*> -> applySelectTab(navigation, patch.unsafeCast())
        }
    }

    var newValues: MutableMap<TKey, TValue>? = null

    for (tab in navigation.tabs) {
        val newValue = tabsReducers.invoke(tab.data, patch)

        if (newValue != tab.data) {
            if (newValues == null) {
                newValues = mutableMapOf()
            }
            newValues[tab.key] = newValue
        }
    }

    if (newValues != null) {
        tabs = tabs.map { tab ->
            newValues[tab.key]?.let { data ->
                Tab<TKey, TValue>(
                    key = tab.key,
                    data = data
                )
            } ?: tab
        }.toImmutableList()
    }

    return navigation
}

@PublishedApi
internal fun <TKey, TValue> applyAddTab(
    navigation: Navigation<TKey, TValue>,
    patch: AddTab<TKey, TValue>
) : Navigation<TKey, TValue> {
    return navigation.copy(
        tabs = persistentListOf(patch.tab).addAll(navigation.tabs)
    )
}

@PublishedApi
internal fun <TKey, TValue> applySelectTab(
    navigation: Navigation<TKey, TValue>,
    patch: SelectTab<TKey>
) : Navigation <TKey, TValue>{
    return navigation.copy(
        activeTabKey = patch.tabKey
    )
}
