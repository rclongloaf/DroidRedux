package com.rcll.droidredux.redux

import com.rcll.droidredux.redux.domain.domainModule
import com.rcll.droidredux.redux.entities.entitiesModule
import com.rcll.droidredux.redux.reducer.AppStateReducer
import com.rcll.mainscreen.mainScreenModule
import com.rcll.timerservice.timerModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appStateModule = module {
    singleOf(::AppStateReducer)

    includes(
        domainModule,
        entitiesModule,
        mainScreenModule,
        timerModule
    )
}