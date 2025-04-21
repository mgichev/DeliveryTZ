package com.deliverytz.ui.screens

import com.deliverytz.domain.LoginType

/**
 * Класс реализует бизнес логику страницы авторизации.
 * Не хранит состояния.
 * Позволяет проверить корректность почты и номера телефона
 */
class LoginViewModel {

    /**
     * Проверяет корректность переданного value относительно его LoginType
     */
    fun checkIsLoginValid(loginType: LoginType, value: String) : Boolean {

        if(value.isEmpty())
            return false

        if (loginType == LoginType.EMAIL) {
            return checkIsEmailValid(value)
        } else {
            return checkIsPhoneValid(value)
        }
    }

    private fun checkIsEmailValid(email: String) : Boolean {
        for (i in email) {
            if(!i.isLetterOrDigit() && i != '.' && i != '@') return false
        }

        // проверка недопустимых комбинаций
        if (email.contains(".@")
            || (email.contains("@."))
            || (email.contains(".."))
            || (email.contains("@@"))
            || (email.first() == '.' || email.first() == '@')
            || (email.last() == '.' || email.last() == '@')) {
            return false
        }

        // обязательное наличие @
        if (!email.contains("@")) {
            return false
        }

        val list = email.split("@")

        // может быть только 1 @
        if (list.size > 2)
            return false

        // вторая часть почты обязательно содержит .
        if (!list[1].contains("."))
            return false

        return true
    }

    private fun checkIsPhoneValid(phone: String) : Boolean {

        // проверка авторизации по номеру упрощена


        if (phone.length < 10 || phone.length > 11)
            return false

        if (phone.substring(0, 2) != "+7" && phone.first() != '8')
            return false

        for (i in phone) {
            if(!i.isDigit() && i != '+') return false
        }
        return true
    }

}