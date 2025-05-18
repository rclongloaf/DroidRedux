package com.rcll.droidredux.redux

import com.rcll.core.base.BaseStore
import com.rcll.core.middlewares.concat.ConcatMiddleware
import com.rcll.core.middlewares.concat.ConcatReducersProvider
import com.rcll.core.middlewares.dynamic.DynamicMiddleware
import com.rcll.core.middlewares.dynamic.manager.DynamicActionObserversManager
import com.rcll.core.middlewares.rollback.RollbackMiddleware
import com.rcll.droidredux.redux.reducer.AppStateReducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.java.KoinJavaComponent.get

class AppStore(
    concatReducersProvider: ConcatReducersProvider,
    dynamicActionObserversManager: DynamicActionObserversManager
) : BaseStore<AppState>(
    initialState = AppState(),
    middlewares = listOf(
        AppCompositionMiddleware(),
        RollbackMiddleware(),
        ConcatMiddleware(concatReducersProvider),
        DynamicMiddleware(dynamicActionObserversManager)
    ),
    rootReducer = get(AppStateReducer::class.java),
    scope = CoroutineScope(
        context = Dispatchers.Default.limitedParallelism(
            parallelism = 1,
            name = "StoreThread"
        ) + SupervisorJob()
    )
)
