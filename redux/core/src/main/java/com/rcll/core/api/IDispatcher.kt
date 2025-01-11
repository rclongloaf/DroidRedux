package com.rcll.core.api

interface IDispatcher {
    fun dispatch(action: IAction)
}
