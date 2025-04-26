package com.rcll.domain.cache

import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId

interface UsersCacheSelector {
    fun contains(state: Any, id: UserId): Boolean
    fun get(state: Any, id: UserId): UserData
    fun getOrNull(state: Any, id: UserId): UserData?
}