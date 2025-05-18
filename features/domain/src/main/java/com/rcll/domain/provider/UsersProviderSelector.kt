package com.rcll.domain.provider

import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId
import com.rcll.domain.status.StatusLCER

interface UsersProviderSelector {
    fun isFetching(state: Any, id: UserId): Boolean
    fun hasError(state: Any, id: UserId): Boolean
    fun getStatusLCER(state: Any, id: UserId): StatusLCER<UserData, UserRequestError>
}