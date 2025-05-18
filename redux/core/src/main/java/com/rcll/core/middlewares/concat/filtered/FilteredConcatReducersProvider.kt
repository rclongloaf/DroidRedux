package com.rcll.core.middlewares.concat.filtered

import com.rcll.core.api.Action
import kotlinx.collections.immutable.ImmutableCollection
import kotlin.reflect.KClass

typealias FilteredConcatReducers = ImmutableCollection<FilteredConcatReducer<out Action>>

interface FilteredConcatReducersProvider {
    /**
     * @return Всегда возвращает одинаковую коллекцию [FilteredConcatReducer]-ов для
     * соответствующего класса экшена.
     */
    fun getConcatReducers(actionClass: KClass<out Action>): FilteredConcatReducers
}