package com.rcll.droidredux

import android.app.Application
import com.rcll.core.api.IStore
import com.rcll.core.middlewares.dynamic.holder.DynamicMiddlewaresHolder
import com.rcll.core.middlewares.dynamic.holder.DynamicMiddlewaresHolderImpl
import com.rcll.core.ui.UIStateProvider
import com.rcll.droidredux.redux.AppStore
import com.rcll.droidredux.redux.appStateModule
import com.rcll.droidredux.ui.AppUIStateProvider
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.bind
import org.koin.dsl.module

class MyApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}

val appModule = module {
    single<DynamicMiddlewaresHolder> { DynamicMiddlewaresHolderImpl() }
    singleOf(::AppStore) bind IStore::class withOptions {
        createdAtStart()
    }
    singleOf(::AppUIStateProvider) bind UIStateProvider::class withOptions {
        createdAtStart()
    }

    includes(appStateModule)
}

