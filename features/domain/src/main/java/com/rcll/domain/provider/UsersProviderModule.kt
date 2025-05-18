package com.rcll.domain.provider

import com.rcll.domain.provider.reducer.UsersProviderReducer
import com.rcll.domain.provider.reducer.UsersProviderReducerImpl
import org.koin.dsl.module

val usersProviderModule = module {
    single<UsersProviderReducer> { UsersProviderReducerImpl() }
}