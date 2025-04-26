package com.rcll.domain.dto

data class UserData(
    val id: UserId,
    val name: String,
    val age: Int,
)

@JvmInline
value class UserId(val value: Long)