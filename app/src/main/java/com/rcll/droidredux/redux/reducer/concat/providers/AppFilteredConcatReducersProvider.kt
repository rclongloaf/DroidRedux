package com.rcll.droidredux.redux.reducer.concat.providers

import com.rcll.core.api.Action
import com.rcll.core.middlewares.concat.filtered.FilteredConcatReducer
import com.rcll.core.middlewares.concat.filtered.FilteredConcatReducers
import com.rcll.core.middlewares.concat.filtered.FilteredConcatReducersProvider
import com.rcll.domain.provider.reducer.concat.ClearUsersProviderConcatReducer
import com.rcll.domain.provider.reducer.concat.OnFetchSuccessUsersProviderConcatReducer
import com.rcll.domain.provider.reducer.concat.RemoveUsersProviderConcatReducer
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import org.koin.core.component.KoinComponent
import kotlin.reflect.KClass

typealias FilteredConcatReducersMap = ImmutableMap<KClass<out Action>, FilteredConcatReducers>

class AppFilteredConcatReducersProvider : FilteredConcatReducersProvider, KoinComponent {
    private var filteredConcatReducers: FilteredConcatReducersMap? = null

    /**
     * //todo подготавливать списки в своих модулях и затем импортировать их сюда
     */
    private fun createFilteredConcatReducersList(): FilteredConcatReducers = persistentListOf(
        OnFetchSuccessUsersProviderConcatReducer(),
        ClearUsersProviderConcatReducer(),
        RemoveUsersProviderConcatReducer()
    )

    override fun getConcatReducers(actionClass: KClass<out Action>): FilteredConcatReducers {
        var filteredConcatReducers = this.filteredConcatReducers
        if (filteredConcatReducers == null) {
            filteredConcatReducers = createFilteredConcatReducersMap()
        }

        return filteredConcatReducers[actionClass] ?: persistentListOf()
    }

    private fun createFilteredConcatReducersMap(): FilteredConcatReducersMap {
        val filteredConcatReducers = createFilteredConcatReducersList()

        val mutableMap =
            mutableMapOf<KClass<out Action>, MutableList<FilteredConcatReducer<out Action>>>()

        filteredConcatReducers.forEach { concatReducer ->
            var concatReducers = mutableMap[concatReducer.actionFilterClass]
            if (concatReducers == null) {
                concatReducers = mutableListOf()
                mutableMap[concatReducer.actionFilterClass] = concatReducers
            }

            concatReducers.add(concatReducer)
        }

        return persistentMapOf<KClass<out Action>, FilteredConcatReducers>().mutate { mapBuilder ->
            mutableMap.forEach { (key, value) ->
                mapBuilder[key] = persistentListOf<FilteredConcatReducer<out Action>>()
                    .mutate { listBuilder ->
                        listBuilder.addAll(value)
                    }
            }
        }
    }


}