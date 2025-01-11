package com.rcll.core.middlewares

import com.rcll.core.api.IAction
import com.rcll.core.api.IRunnableAction
import com.rcll.core.base.BaseMiddleware

class ThunkMiddleware : BaseMiddleware() {
    override fun dispatch(action: IAction) {
        val currentStore = currentStore ?: return

        if (action is IRunnableAction) {
            action.run(currentStore)
        }

        next?.dispatch(action)
    }
}
