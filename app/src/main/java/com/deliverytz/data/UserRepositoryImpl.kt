package com.deliverytz.data

import android.content.Context
import com.deliverytz.domain.User
import com.deliverytz.domain.UserRepository
import com.google.gson.Gson

class UserRepositoryImpl(context: Context) : UserRepository {


    private val gson = Gson()
    private val sharedPreferences = context.getSharedPreferences(SharedPreferencesConsts.USER_DATA, Context.MODE_PRIVATE)

    override fun addOrUpdateUser(user: User?, loginValue: String?) {
        if (user == null || loginValue == null) return
        with(sharedPreferences.edit()) {
            val json = gson.toJson(user)
            putString(loginValue, json)
            commit()
        }
    }

    override fun getUser(loginValue: String) : User? {
        if (!sharedPreferences.contains(loginValue))
            return null
        val json = sharedPreferences.getString(loginValue, SharedPreferencesConsts.DEFAULT_VALUE)
        val user: User = gson.fromJson(json, User::class.java)
        return user
    }

    override fun removeUser(loginValue: String) {
        sharedPreferences.edit().remove(loginValue).commit()
    }
}

object SharedPreferencesConsts {
    const val USER_DATA = "USER_DATA"
    const val DEFAULT_VALUE = "DEFAULT_VALUE"
}