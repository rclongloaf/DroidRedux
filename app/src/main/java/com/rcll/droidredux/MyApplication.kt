package com.rcll.droidredux

import android.app.Application
import com.rcll.core.api.Store
import com.rcll.core.middlewares.concat.ConcatReducersProvider
import com.rcll.core.middlewares.dynamic.DeferredDynamicActionsHolder
import com.rcll.core.middlewares.dynamic.manager.DynamicActionObserversManager
import com.rcll.core.middlewares.dynamic.manager.DynamicActionObserversManagerImpl
import com.rcll.core.middlewares.dynamic.provider.DynamicActionObserversProvider
import com.rcll.core.middlewares.dynamic.provider.DynamicActionObserversProviderImpl
import com.rcll.core.ui.UIStateProvider
import com.rcll.droidredux.feature.AppFeatureComposition
import com.rcll.droidredux.redux.AppStore
import com.rcll.droidredux.redux.appStateModule
import com.rcll.droidredux.redux.reducer.concat.providers.AppConcatReducersProvider
import com.rcll.droidredux.ui.AppUIMapperComposition
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
    single<ConcatReducersProvider> { AppConcatReducersProvider() }
    single<DeferredDynamicActionsHolder> { DeferredDynamicActionsHolder() }
    single<DynamicActionObserversProvider> { DynamicActionObserversProviderImpl() }
    single<DynamicActionObserversManager> { DynamicActionObserversManagerImpl(get()) }
    singleOf(::AppStore) bind Store::class withOptions {
        createdAtStart()
    }
    singleOf(::AppFeatureComposition) withOptions {
        createdAtStart()
    }
    singleOf(::AppUIStateProvider) bind UIStateProvider::class
    singleOf(::AppUIMapperComposition) withOptions {
        createdAtStart()
    }

    includes(appStateModule)
}

