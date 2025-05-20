package com.rcll.domain.cache

import com.rcll.core.api.Action
import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId

sealed interface UsersCacheAction : Action {
    data class Add(val id: UserId, val data: UserData) : UsersCacheAction
    data class Update(val id: UserId, val data: UserData) : UsersCacheAction
    data class Remove(val id: UserId) : UsersCacheAction
    data object Clear : UsersCacheAction
}