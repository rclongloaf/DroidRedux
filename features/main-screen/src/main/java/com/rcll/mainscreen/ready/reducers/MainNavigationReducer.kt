package com.rcll.mainscreen.ready.reducers

import com.rcll.core.api.Action
import com.rcll.core.api.Reducer
import com.rcll.mainscreen.ready.MainTabKey
import com.rcll.navigation.NavigationReducer
import com.rcll.navigation.NavigationReducerImpl

interface MainNavigationReducer : NavigationReducer<MainTabKey, String>

class MainNavigationReducerImpl : NavigationReducerImpl<MainTabKey, String>(
    tabReducer = object : Reducer<String> {
        override fun reduce(state: String, action: Action): String {
            return state
        }
    }
), MainNavigationReducer