package com.deliverytz.domain

interface UserRepository {
    fun addOrUpdateUser(user: User?, loginValue: String?)

    fun getUser(loginValue: String) : User?

    fun removeUser(loginValue: String)
}