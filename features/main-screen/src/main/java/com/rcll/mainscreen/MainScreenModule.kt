package com.rcll.mainscreen

import com.rcll.mainscreen.common.reducer.MainScreenReducer
import com.rcll.mainscreen.common.reducer.MainScreenReducerImpl
import com.rcll.mainscreen.common.reducer.concat.MainScreenSubConcatReducer
import com.rcll.mainscreen.common.reducer.concat.MainScreenSubConcatReducerImpl
import com.rcll.mainscreen.ready.reducers.MainNavigationReducer
import com.rcll.mainscreen.ready.reducers.MainNavigationReducerImpl
import com.rcll.mainscreen.ready.reducers.MainScreenReadyReducer
import com.rcll.mainscreen.ready.reducers.MainScreenReadyReducerImpl
import org.koin.dsl.module

val mainScreenModule = module {
    single<MainNavigationReducer> { MainNavigationReducerImpl() }
    single<MainScreenReadyReducer> { MainScreenReadyReducerImpl() }
    single<MainScreenReducer> { MainScreenReducerImpl() }

    single<MainScreenSubConcatReducer> { MainScreenSubConcatReducerImpl() }
}