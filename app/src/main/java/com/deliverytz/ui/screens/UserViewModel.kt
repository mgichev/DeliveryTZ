package com.deliverytz.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deliverytz.domain.LoginType
import com.deliverytz.domain.User
import com.deliverytz.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _user: MutableLiveData<User?> = MutableLiveData()
    val user: LiveData<User?>
        get() = _user

    /**
     * Открывает сессию для попытки входа в аккаунт, не получая при этом пользовательских данных
     */
    fun tryLoginUser(phone: String, email: String) {

        val loginType = if (phone.isBlank()) LoginType.EMAIL else LoginType.PHONE_NUMBER

        _user.value = User(name = "Имя", phone = phone, email = email, loginType = loginType)
    }

    fun fetchData() {
        val loginValue = user.value?.email?.ifBlank { user.value?.phone } ?: return
        val userData = userRepository.getUser(loginValue)
        userData?.let {
            _user.value = it
        }
    }

    fun logout() {
        _user.value = null
    }

    fun saveData(user: User) {
        val loginValue = if(user.email.isBlank()) user.phone else user.email
        userRepository.addOrUpdateUser(user, loginValue)
    }

    fun removeAccount() {
        val loginValue = user.value?.email?.ifBlank { user.value?.phone } ?: return
        userRepository.removeUser(loginValue)
        logout()
    }


}