package com.rcll.core.middlewares.concat

import kotlinx.collections.immutable.ImmutableCollection

interface ConcatReducersProvider {
    /**
     * Всегда возвращает одинаковую коллекцию [ConcatReducer]-ов.
     */
    fun getConcatReducers(): ImmutableCollection<ConcatReducer>
}