package com.rcll.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LateinitMutableState<T : Any>(
    private var state: MutableState<T?>,
) : ReadWriteProperty<Any?, T>, MutableState<T> {

    override var value: T
        get() = state.value ?: throw UninitializedPropertyAccessException(
            "Lateinit property has not been initialized"
        )
        set(value) {
            state.value = value
        }

    val isInitialized: Boolean
        get() = state.value != null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun component1(): T = value
    override fun component2(): (T) -> Unit = { value = it }
}

/**
 * Ключевые особенности:
 *
 * 1. Бросает UninitializedPropertyAccessException при попытке чтения до инициализации
 * 2. Интегрируется с системой реактивности Compose через делегирование к MutableState<T?>
 * 3. Поддерживает стандартные политики сравнения значений
 * 4. Позволяет проверять инициализацию через isInitialized
 * 5. Реализует ReadWriteProperty для использования с делегатом by
 *
 * Для безопасного использования в UI:
 * 1. Всегда инициализируйте состояние до использования в композиции
 * 2. Используйте derivedStateOf для отслеживания состояния инициализации
 * 3. Обрабатывайте состояние загрузки через проверку isInitialized
 *
 * Пример использования:
 *
 *    @Composable
 *    fun Example() {
 *        val userState = remember { lateinitMutableStateOf<User>() }
 *
 *        LaunchedEffect(Unit) {
 *            delay(1000)
 *            userState.value = fetchUser() // Инициализация
 *        }
 *
 *        // Проверка инициализации через derivedStateOf
 *        val isInitialized by remember { derivedStateOf { userState.isInitialized } }
 *
 *        if (isInitialized) {
 *            Text(userState.value.name)
 *        } else {
 *            CircularProgressIndicator()
 *        }
 *    }
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> lateinitMutableState(
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): LateinitMutableState<T> {
    return LateinitMutableState(
        mutableStateOf(
            null,
            policy = policy as SnapshotMutationPolicy<T?>
        )
    )
}

@Composable
fun <T : Any> rememberLateinitMutableState(
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
): LateinitMutableState<T> = remember { lateinitMutableState(policy) }