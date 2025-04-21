package com.deliverytz.domain

/**
 * Состояние авторизации
 * ATTEMPT_LOGIN (если пользователь пытается войти в первый раз)
 * ERROR (если пользователь ввел неправильный код)
 */
enum class LoginStatus {
    ATTEMPT_LOGIN, ERROR
}