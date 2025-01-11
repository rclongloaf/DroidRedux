package com.rcll.droidredux.redux

import com.rcll.core.base.BaseStore
import com.rcll.core.middlewares.DynamicDispatcherMiddleware
import com.rcll.core.middlewares.ThunkMiddleware

class AppStore : BaseStore<AppState>(
    initialState = AppState(),
    middlewares = listOf(ThunkMiddleware(), DynamicDispatcherMiddleware()),
    rootReducer = ::reduceApp
)
