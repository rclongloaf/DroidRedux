package com.rcll.mainscreen.ready.reducers

import com.rcll.core.api.IAction
import com.rcll.core.api.IReducer
import com.rcll.mainscreen.ready.MainTabKey
import com.rcll.navigation.NavigationReducer
import com.rcll.navigation.NavigationReducerImpl

interface MainNavigationReducer : NavigationReducer<MainTabKey, String>

class MainNavigationReducerImpl : NavigationReducerImpl<MainTabKey, String>(object :
    IReducer<String> {
    override fun reduce(state: String, action: IAction): String {
        return state
    }
}), MainNavigationReducer