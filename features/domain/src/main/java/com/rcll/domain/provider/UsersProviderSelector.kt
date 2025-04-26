package com.rcll.domain.provider

import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId
import com.rcll.domain.status.StatusLCER

interface UsersProviderSelector {
    fun get(state: Any, id: UserId): StatusLCER<UserData, UserRequestError>
}