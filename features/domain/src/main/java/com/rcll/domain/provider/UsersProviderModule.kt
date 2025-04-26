package com.rcll.domain.provider

import org.koin.dsl.module

val usersProviderModule = module {
    single<UsersProviderReducer> { UsersProviderReducerImpl() }
}