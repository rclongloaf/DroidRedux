package com.rcll.droidredux.redux.entities.providers

import com.rcll.domain.provider.UsersProvider

data class Providers(
    val usersProvider: UsersProvider = UsersProvider()
) {
    fun smartCopy(
        usersProvider: UsersProvider
    ): Providers {
        if (usersProvider !== this.usersProvider) {
            return Providers(
                usersProvider = usersProvider
            )
        }
        return this
    }
}