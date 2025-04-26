package com.rcll.domain.cache

import org.koin.dsl.module

val usersCacheModule = module {
    single<UsersCacheReducer> { UsersCacheReducerImpl() }
}