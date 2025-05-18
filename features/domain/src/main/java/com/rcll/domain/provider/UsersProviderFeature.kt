package com.rcll.domain.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import com.rcll.core.base.LocalStoreDispatcher
import com.rcll.domain.dto.UserData
import kotlinx.coroutines.delay

@Composable
fun UsersProviderFeature(
    usersProvider: UsersProvider
) {
    val dispatcher = LocalStoreDispatcher.current

    usersProvider.fetchingStatusSet.forEach { userId ->
        key(userId) {
            LaunchedEffect(userId) {
                delay(1000)
                dispatcher.dispatch(
                    UsersProviderAction.OnFetchSuccess(
                        id = userId,
                        data = UserData(
                            id = userId,
                            name = "User $userId",
                            age = (12..100).random()
                        )
                    )
                )
            }
        }
    }
}