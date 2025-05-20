package com.rcll.core.middlewares.parallel

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.snapshots.SnapshotApplyConflictException
import com.rcll.core.api.Action
import com.rcll.core.base.BaseMiddleware
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

/**
 * Многопоточная обработка экшенов, которая основывается на использовании MutableState-ов
 * в дереве стейта.
 *
 * Требования и ограничения:
 * 1. Корневой объект стейта не должен изменяться, т.е. все изменения должны происходить
 * во вложенных MutableState-ах.
 * 2. Вызов этого миддлвара должен происходить из одного потока.
 * 3. Новый стейт применяется в этой миддлваре. Нужно учитывать это при построении цепочки:
 * не оборачивать в дополнительные снепшоты и не делать никаких изменений в цепочке
 * до этой миддлвары.
 * 4. Неизменность стейта для цепочки до этой миддлвары гарантируется при условии,
 * что цепочка от стора до этой миддлвары выполняется без прерываний.
 *
 * @param maxParallelTasks Максимальное количество параллельных обработчиков. Чем больше значение,
 * тем больше вероятность конфликта, при которых будет потрачено больше времени на синхронизацию.
 * Для меньшего количества конфликтов следует максимально нормализовать стейт
 * и корректно обрабатывать чтение и запись MutableState-ов с помощью фильтрации по ключам
 * и выбрасывании ошибок транзакции.
 */
class ParallelMiddleware<TState : Any>(
    private val maxParallelTasks: Int
) : BaseMiddleware<TState>() {
    private var activeParallelTasksCount = 0
    private var isLockedForSyncTasks = false

    @OptIn(ExperimentalComposeApi::class)
    override suspend fun consumeAsync(state: TState, action: Action) {

        /**
         * Многопоточные блокировки не нужны, т.к. Store работает на диспатчере с parallelism(1)
         */
        while (activeParallelTasksCount >= maxParallelTasks || isLockedForSyncTasks) {
            yield()
        }
        activeParallelTasksCount++


        val snapshot = ConflictAwareSnapshot()
        try {
            withContext(Dispatchers.Default + snapshot.asContextElement()) {
                consumeNextAsync(state, action)
            }
            /**
             * Выходить из withContext будут по одному,
             * т.к. Store работает на диспатчере с parallelism(1)
             */
            snapshot.apply().check()
        } catch (e: SnapshotApplyConflictException) {
            /**
             * Если при слиянии снепшота возникает конфликт,
             * то обрабатываем экшен повторно и синхронним с активными тасками.
             */
            isLockedForSyncTasks = true
            consumeNextAsync(state, action)
        } finally {
            snapshot.dispose()

            activeParallelTasksCount--
            if (activeParallelTasksCount == 0) {
                /**
                 * Когда все активные таски завершили синхронизацию,
                 * то снова переключаемся на параллельный режим.
                 */
                isLockedForSyncTasks = false
            }
        }
    }

    override fun consume(state: TState, action: Action) {
        throw IllegalStateException("ParallelMiddleware required consumeAsync call")
    }
}