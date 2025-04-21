package com.deliverytz.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.deliverytz.R
import com.deliverytz.domain.LoginStatus
import com.deliverytz.domain.LoginType
import com.deliverytz.domain.TIMER_MAX_VALUE
import com.deliverytz.domain.User
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

/**
 * Отображает экран для получения кода из смс/почты.
 * Аннотация UnusedMaterial3ScaffoldPaddingParamete используется для игнорирования padding AppBar.
 * В данном случае padding не требуется
 * @param onNextBtnClicked должен вызывать content CodeScreen при успешном вводе кода
 * @param onBackBtnClicked вызывается при нажатии на кнопку назад
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeScreen(
    modifier: Modifier = Modifier,
    user: User?,
    onNextBtnClicked: () -> Unit,
    onBackBtnClicked: () -> Unit,
) {
    Scaffold(topBar = {
        TopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = onBackBtnClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                )
            }
        })
    }) {
        val loginType = user?.loginType ?: LoginType.EMAIL
        val loginValue = if (user?.loginType == LoginType.EMAIL) user.email else user?.phone ?: ""
        GetCodeItem(
            modifier = modifier,
            loginType = loginType,
            loginValue = loginValue,
            onNextBtnClicked = onNextBtnClicked
        )
    }
}

/**
 * Контент CodeScreen
 * @param loginType тип авторизации
 * @param loginValue значение поля авторизации из LoginScreen
 * @param onNextBtnClicked вызывается при нажатии на кнопку Продолжить
 */
@Composable
fun GetCodeItem(
    modifier: Modifier = Modifier,
    loginValue: String,
    loginType: LoginType = LoginType.EMAIL,
    onNextBtnClicked: () -> Unit
) {

    val codeViewModel: CodeViewModel = CodeViewModel()

    var userInputCode by rememberSaveable { mutableStateOf("") }
    var isCodeEntered by rememberSaveable { mutableStateOf(false) }
    var timer by rememberSaveable { mutableIntStateOf(TIMER_MAX_VALUE) }
    var isTimerWork by rememberSaveable { mutableStateOf(true) }
    var loginStatus by rememberSaveable { mutableStateOf(LoginStatus.ATTEMPT_LOGIN) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.weight(4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Icon(
                Icons.Outlined.Home, null, modifier = Modifier.requiredSize(52.dp)
            )
            Text(
                text = if (loginType == LoginType.EMAIL) stringResource(R.string.code_from_email) else stringResource(
                    R.string.code_from_sms
                ), style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = if (loginType == LoginType.EMAIL) stringResource(
                    R.string.code_is_send_to_email, loginValue
                ) else stringResource(
                    R.string.code_is_send_to_number, loginValue
                ), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 32.dp, 16.dp, 0.dp),
                value = userInputCode,
                onValueChange = {
                    userInputCode = it
                    if (it.length == 4) isCodeEntered = true
                    else {
                        isCodeEntered = false
                        loginStatus = LoginStatus.ATTEMPT_LOGIN
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedIndicatorColor = if (loginStatus == LoginStatus.ERROR) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )


        }

        Column(
            modifier = Modifier.weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.weight(1f))

            if (loginStatus != LoginStatus.ATTEMPT_LOGIN) {
                Text(
                    text = stringResource(R.string.wrong_code),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.weight(1f))
            }

            if (!isTimerWork) TextButton(onClick = {
                timer = TIMER_MAX_VALUE
                isTimerWork = true
            }) {
                Text(
                    text = stringResource(R.string.resend_code),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            } else {
                LaunchedEffect(key1 = timer) {
                    if (timer > 0) {
                        delay(1.seconds)
                        timer--
                    } else isTimerWork = false
                }

                Text(
                    text = stringResource(R.string.resend_code_time, timer),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Spacer(if (loginStatus == LoginStatus.ERROR) Modifier.weight(1f) else Modifier.weight(2f))


            Button(
                enabled = isCodeEntered,
                onClick = {
                    if (!codeViewModel.verifyCode(userInputCode)) loginStatus =
                        LoginStatus.ERROR else onNextBtnClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                colors = if (isCodeEntered) ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                else ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)

            ) {
                Text(
                    text = stringResource(R.string.to_next),
                    color = if (isCodeEntered) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = if (loginType == LoginType.EMAIL) stringResource(R.string.not_get_email) else stringResource(
                    R.string.not_get_phone
                ),
                modifier = Modifier.padding(top = 16.dp, bottom = 34.dp),
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )

        }
    }
}

