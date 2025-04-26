package com.rcll.droidredux.redux.entities

import com.rcll.domain.provider.UsersProviderSelector
import com.rcll.domain.provider.usersProviderModule
import com.rcll.droidredux.redux.entities.providers.ProvidersReducer
import com.rcll.droidredux.redux.entities.providers.ProvidersReducerImpl
import com.rcll.droidredux.redux.entities.providers.ProvidersSelector
import com.rcll.droidredux.redux.entities.providers.users.UsersProviderSelectorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val entitiesModule = module {
    single<EntitiesReducer> { EntitiesReducerImpl() }
    singleOf(::EntitiesSelector)

    single<ProvidersReducer> { ProvidersReducerImpl() }
    singleOf(::ProvidersSelector)

    single<UsersProviderSelector> { UsersProviderSelectorImpl() }

    includes(usersProviderModule)
}