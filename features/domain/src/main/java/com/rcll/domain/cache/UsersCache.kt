package com.rcll.domain.cache

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

@Stable
interface UsersCache {
    val usersMap: State<PersistentMap<UserId, State<UserData>>>
}

data class MutableUsersCache(
    override val usersMap: MutableState<PersistentMap<UserId, MutableState<UserData>>> = mutableStateOf(
        persistentMapOf()
    )
) : UsersCache