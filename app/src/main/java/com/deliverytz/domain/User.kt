package com.deliverytz.domain

data class User(
    val name: String,
    val phone: String,
    val email: String,
    val birthday: String = "",
    val loginType: LoginType,
    val address: String = "",
    val apartment: String = "",
    val entrance: String = "",
    val floor: String = "",
    val apartmentPhone: String = ""
)