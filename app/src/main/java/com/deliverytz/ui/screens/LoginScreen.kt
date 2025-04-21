package com.deliverytz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deliverytz.R
import com.deliverytz.domain.LoginType


/**
 * Экран с вводом номера телефона/почты для авторизации
 * @param onNextBtnClick вызывается при нажатии на кнопку Отправить код
 * @param onSkipBtnClick вызывается при нажатии на кнопку Пропустить
 */
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNextBtnClick: (email: String, phone: String) -> Unit,
    onSkipBtnClick: () -> Unit
) {

    val authViewModel = LoginViewModel()
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var loginType by rememberSaveable { mutableStateOf(LoginType.EMAIL) }
    var isCanAuth by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.weight(4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Icon(
                Icons.Outlined.Home, null, modifier = Modifier.requiredSize(52.dp)
            )
            Text(
                text = if (loginType == LoginType.EMAIL) stringResource(R.string.auth_email) else stringResource(
                    R.string.auth_number
                ), style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(R.string.get_bonus_text),
                style = MaterialTheme.typography.bodyLarge
            )
            if (loginType == LoginType.EMAIL) TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp, 16.dp, 0.dp),
                value = email,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                ),
                onValueChange = {
                    email = it
                    isCanAuth = authViewModel.checkIsLoginValid(loginType, it)
                },
                placeholder = { Text(stringResource(R.string.email_example)) })
            else if (loginType == LoginType.PHONE_NUMBER) TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp, 16.dp, 0.dp),
                value = phone,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                ),
                onValueChange = {
                    phone = it
                    isCanAuth = authViewModel.checkIsLoginValid(loginType, it)
                },
                placeholder = { Text(stringResource(R.string.phone_number_example)) })


        }
        Column(
            modifier = Modifier.weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            TextButton(onClick = {
                loginType =
                    if (loginType == LoginType.EMAIL) LoginType.PHONE_NUMBER else LoginType.EMAIL
            }) {
                Text(
                    text = if (loginType == LoginType.EMAIL) stringResource(R.string.auth_more_number)
                    else stringResource(R.string.auth_more_email),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.weight(1f))
            Button(
                enabled = isCanAuth,
                onClick = { onNextBtnClick(email, phone) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = if (loginType == LoginType.EMAIL) stringResource(R.string.send_code)
                    else stringResource(R.string.send_code_number),
                    color = if (isCanAuth) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            TextButton(
                onClick = { onSkipBtnClick() },
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.skip_btn),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.privacy_policy_not_link))
                    pushStringAnnotation(
                        "Privacy policy", annotation = "https://google.com"
                    )
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(" " + stringResource(R.string.privacy_policy_link))
                    }
                    pop()
                },
                modifier = Modifier.padding(top = 24.dp, bottom = 34.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }


}

