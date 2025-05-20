package com.rcll.droidredux.redux.entities.providers

import androidx.compose.runtime.Stable
import com.rcll.domain.provider.MutableUsersProvider
import com.rcll.domain.provider.UsersProvider

@Stable
interface Providers {
    val usersProvider: UsersProvider
}

data class MutableProviders(
    override val usersProvider: MutableUsersProvider = MutableUsersProvider()
) : Providers {
    fun smartCopy(
        usersProvider: MutableUsersProvider
    ): MutableProviders {
        if (usersProvider !== this.usersProvider) {
            return MutableProviders(
                usersProvider = usersProvider
            )
        }
        return this
    }
}