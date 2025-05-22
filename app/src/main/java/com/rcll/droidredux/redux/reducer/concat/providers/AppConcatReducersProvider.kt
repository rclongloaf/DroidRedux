package com.rcll.droidredux.redux.reducer.concat.providers

import com.rcll.core.middlewares.concat.ConcatReducer
import com.rcll.core.middlewares.concat.ConcatReducersProvider
import com.rcll.core.middlewares.concat.filtered.ConcatReducersFilter
import com.rcll.droidredux.redux.reducer.concat.AppStateConcatReducer
import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.persistentListOf

class AppConcatReducersProvider : ConcatReducersProvider {
    private val concatReducers: ImmutableCollection<ConcatReducer> by lazy {
        persistentListOf(
            AppStateConcatReducer(),
            ConcatReducersFilter(AppFilteredConcatReducersProvider())
        )
    }

    override fun getConcatReducers(): ImmutableCollection<ConcatReducer> {
        return concatReducers
    }
}
