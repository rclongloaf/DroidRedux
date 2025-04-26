package com.rcll.droidredux.redux

import com.rcll.core.base.BaseStore
import com.rcll.core.middlewares.ThunkMiddleware
import com.rcll.core.middlewares.dynamic.DynamicDispatcherMiddlewares
import com.rcll.core.middlewares.dynamic.holder.DynamicMiddlewaresHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.java.KoinJavaComponent.get

class AppStore(
    dynamicMiddlewaresHolder: DynamicMiddlewaresHolder
) : BaseStore<AppState>(
    initialState = AppState(),
    middlewares = listOf(
        AppCompositionMiddleware(),
        ThunkMiddleware(),
        DynamicDispatcherMiddlewares(dynamicMiddlewaresHolder)
    ),
    rootReducer = get(AppStateReducer::class.java),
    scope = CoroutineScope(
        context = Dispatchers.Default.limitedParallelism(
            parallelism = 1,
            name = "StoreThread"
        ) + SupervisorJob()
    )
)
