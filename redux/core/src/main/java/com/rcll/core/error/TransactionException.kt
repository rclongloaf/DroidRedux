package com.rcll.core.error

/**
 * Ошибка, которая произошла при транзакции.
 */
open class TransactionException(message: String) : Throwable(message)

/**
 * Критическая ошибка транзакции, которую следует прокинуть наружу.
 */
open class FatalTransactionException(message: String) : TransactionException(message)

/**
 * Не критичная ошибка транзакции, которую можно проигнорировать.
 */
open class NonFatalTransactionException(message: String) : TransactionException(message)

/**
 * Экшен уже не актуален. Используется для предотвращения спама экшенов для ивентов, которые должны
 * обрабатываться разово.
 */
class IllegalActionTokenException(message: String) : NonFatalTransactionException(message)

/**
 * Стейт не консистентен. Используется когда не сходятся ожидания текущего стейта транзакции.
 */
class NotConsistentStateException(message: String) : NonFatalTransactionException(message)

fun illegalActionToken(message: String) {
    throw IllegalActionTokenException(message)
}

fun notConsistentState(message: String) {
    throw NotConsistentStateException(message)
}
