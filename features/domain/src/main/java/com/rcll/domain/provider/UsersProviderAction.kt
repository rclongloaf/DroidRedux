package com.rcll.domain.provider

import com.rcll.core.api.Action
import com.rcll.domain.dto.UserData
import com.rcll.domain.dto.UserId

sealed interface UsersProviderAction : Action {
    data class Fetch(val id: UserId) : UsersProviderAction
    data class OnFetchSuccess(val id: UserId, val data: UserData) : UsersProviderAction
    data class OnFetchError(val id: UserId, val error: UserRequestError) : UsersProviderAction

    data class Remove(val id: UserId) : UsersProviderAction
    data object Clear : UsersProviderAction
}