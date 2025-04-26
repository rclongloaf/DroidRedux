package com.rcll.domain.provider

import com.rcll.domain.dto.UserId
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentHashSetOf

data class UsersProvider(
    val fetchingStatusSet: PersistentSet<UserId> = persistentHashSetOf(),
    val errorStatusMap: PersistentMap<UserId, UserRequestError> = persistentHashMapOf()
) {
    fun smartCopy(
        fetchingStatusSet: PersistentSet<UserId>,
        errorStatusMap: PersistentMap<UserId, UserRequestError>,
    ): UsersProvider {
        if (this.fetchingStatusSet !== fetchingStatusSet ||
            this.errorStatusMap !== errorStatusMap
        ) {
            return UsersProvider(
                fetchingStatusSet = fetchingStatusSet,
                errorStatusMap = errorStatusMap
            )
        }
        return this
    }
}

sealed interface UserRequestError {
    data object UserNotFound : UserRequestError
    data object NetworkError : UserRequestError
}