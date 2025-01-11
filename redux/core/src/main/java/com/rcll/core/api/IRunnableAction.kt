package com.rcll.core.api

interface IRunnableAction : IAction {
    fun run(store: IStore<*>)
}
