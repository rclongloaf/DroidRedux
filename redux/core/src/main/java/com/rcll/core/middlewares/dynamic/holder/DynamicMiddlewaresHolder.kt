package com.rcll.core.middlewares.dynamic.holder

import com.rcll.core.api.IAction
import com.rcll.core.middlewares.dynamic.DynamicMiddleware

interface DynamicMiddlewaresHolder {
    val dynamicMiddlewares: DynamicMiddlewaresImmutableMap

    fun add(dynamicMiddleware: DynamicMiddleware<out IAction>)

    fun remove(dynamicMiddleware: DynamicMiddleware<out IAction>)
}