package com.rcll.droidredux.redux.domain

import com.rcll.domain.cache.UsersCacheSelector
import com.rcll.domain.cache.usersCacheModule
import com.rcll.droidredux.redux.domain.users.UsersCacheSelectorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    single<DomainReducer> { DomainReducerImpl() }
    singleOf(::DomainSelector)
    single<UsersCacheSelector> { UsersCacheSelectorImpl() }

    includes(usersCacheModule)
}