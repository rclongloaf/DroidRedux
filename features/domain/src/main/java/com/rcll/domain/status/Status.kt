package com.rcll.domain.status

interface LoadingStatus {
    val isLoading: Boolean
}

interface ContentStatus<out T> {
    val hasData: Boolean
    val data: T
}

interface ErrorStatus<out E> {
    val hasError: Boolean
    val error: E
}

/**
 * This class represents combination of Load Content Error Retry statuses
 */
sealed interface StatusLCER<out T, out E> : LoadingStatus, ContentStatus<T>, ErrorStatus<E> {
    data object Loading : StatusLCER<Nothing, Nothing> {
        override val isLoading get() = true
        override val hasData get() = false
        override val data get() = error("No content")
        override val hasError get() = false
        override val error get() = error("No error")
    }

    data class Content<T>(override val data: T) : StatusLCER<T, Nothing> {
        override val isLoading get() = false
        override val hasData get() = true
        override val hasError get() = false
        override val error get() = error("No error")
    }

    data class Error<E>(override val error: E) : StatusLCER<Nothing, E> {
        override val isLoading get() = false
        override val hasData get() = false
        override val data get() = error("No content")
        override val hasError get() = true
    }

    data class ContentWithLoading<T>(override val data: T) : StatusLCER<T, Nothing> {
        override val isLoading get() = true
        override val hasData get() = true
        override val hasError get() = false
        override val error get() = error("No error")
    }

    data class ContentWithError<T, E>(override val data: T, override val error: E) :
        StatusLCER<T, E> {
        override val isLoading get() = false
        override val hasData get() = true
        override val hasError get() = true
    }
}

/**
 * This class represents combination of Load Content Error statuses
 */
sealed interface StatusLCE<out T, out E> : LoadingStatus, ContentStatus<T>, ErrorStatus<E> {
    data object Loading : StatusLCE<Nothing, Nothing> {
        override val isLoading get() = true
        override val hasData get() = false
        override val data get() = error("No content")
        override val hasError get() = false
        override val error get() = error("No error")
    }

    data class Content<T>(override val data: T) : StatusLCE<T, Nothing> {
        override val isLoading get() = false
        override val hasData get() = true
        override val hasError get() = false
        override val error get() = error("No error")
    }

    data class Error<E>(override val error: E) : StatusLCE<Nothing, E> {
        override val isLoading get() = false
        override val hasData get() = false
        override val data get() = error("No content")
        override val hasError get() = true
    }
}

sealed interface StatusLC<out T> : LoadingStatus, ContentStatus<T> {
    data object Loading : StatusLC<Nothing> {
        override val isLoading get() = true
        override val hasData get() = false
        override val data get() = error("No content")
    }

    data class Content<T>(override val data: T) : StatusLC<T> {
        override val isLoading get() = false
        override val hasData get() = true
    }

    data class LoadingWithContent<T>(override val data: T) : StatusLC<T> {
        override val isLoading get() = true
        override val hasData get() = true
    }
}