package com.rcll.timerservice

import org.koin.dsl.module

val timerModule = module {
    single<TimerReducer> { TimerReducerImpl() }
}