package com.rcll.domain.provider

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.rcll.domain.dto.UserId
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentHashSetOf

@Stable
interface UsersProvider {
    val fetchingStatusSet: State<PersistentSet<UserId>>
    val errorStatusMap: State<PersistentMap<UserId, UserRequestError>>
}

sealed interface UserRequestError {
    data object UserNotFound : UserRequestError
    data object NetworkError : UserRequestError
}

typealias MutableFetchingStatusSet = MutableState<PersistentSet<UserId>>
typealias MutableErrorStatusMap = MutableState<PersistentMap<UserId, UserRequestError>>

data class MutableUsersProvider(
    override val fetchingStatusSet: MutableFetchingStatusSet = mutableStateOf(persistentHashSetOf()),
    override val errorStatusMap: MutableErrorStatusMap = mutableStateOf(persistentHashMapOf())
) : UsersProvider