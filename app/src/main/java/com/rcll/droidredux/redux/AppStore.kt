package com.rcll.droidredux.redux

import com.rcll.core.base.BaseStore
import com.rcll.core.middlewares.concat.ConcatMiddleware
import com.rcll.core.middlewares.concat.ConcatReducersProvider
import com.rcll.core.middlewares.dynamic.DeferredDynamicActionsHolder
import com.rcll.core.middlewares.dynamic.DynamicDeferredMiddleware
import com.rcll.core.middlewares.dynamic.DynamicListenMiddleware
import com.rcll.core.middlewares.dynamic.provider.DynamicActionObserversProvider
import com.rcll.core.middlewares.rollback.RollbackMiddleware
import com.rcll.droidredux.redux.reducer.AppStateReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.java.KoinJavaComponent.get

class AppStore(
    concatReducersProvider: ConcatReducersProvider,
    dynamicActionObserversProvider: DynamicActionObserversProvider,
    dynamicActionsHolder: DeferredDynamicActionsHolder
) : BaseStore<AppState>(
    initialState = AppState(),
    middlewares = listOf(
        RollbackMiddleware(),
        DynamicDeferredMiddleware(
            deferredDynamicActionsHolder = dynamicActionsHolder
        ),
        ConcatMiddleware(concatReducersProvider),
        DynamicListenMiddleware(
            dynamicActionObserversProvider = dynamicActionObserversProvider,
            deferredDynamicActionsHolder = dynamicActionsHolder
        )
    ),
    rootReducer = get(AppStateReducer::class.java),
    scope = CoroutineScope(
        context = Dispatchers.Default.limitedParallelism(
            parallelism = 1,
            name = "StoreThread"
        ) + SupervisorJob()
    )
)
