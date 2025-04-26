package com.rcll.koin

import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.named

inline fun <reified Type, reified InnerType> Module.genericSingle(
    createdAtStart: Boolean = false,
    noinline definition: Definition<Type>
) = single<Type>(
    qualifier = named<InnerType>(),
    createdAtStart = createdAtStart,
    definition = definition
)