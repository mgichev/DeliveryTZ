package com.deliverytz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var loginType by rememberSaveable { mutableStateOf(LoginType.EMAIL) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = modifier.weight(3f))
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
            onValueChange = { email = it },
            placeholder = { Text(stringResource(R.string.email_example)) })
        else if (loginType == LoginType.PHONE_NUMBER) TextField(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 32.dp, 16.dp, 0.dp),
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text(stringResource(R.string.phone_number_example)) })
        TextButton(onClick = {
            loginType =
                if (loginType == LoginType.EMAIL) LoginType.PHONE_NUMBER else LoginType.EMAIL
        }) {
            Text(
                text = if (loginType == LoginType.EMAIL) stringResource(R.string.auth_more_number)
                else stringResource(R.string.auth_more_email),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = modifier.weight(2f))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = if (loginType == LoginType.EMAIL) stringResource(R.string.send_code)
                else stringResource(R.string.send_code_number),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        TextButton(onClick = { }) {
            Text(
                text = stringResource(R.string.skip_btn),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(
            buildAnnotatedString {
                append(stringResource(R.string.privacy_policy_not_link))
                pushStringAnnotation(
                    "Privacy policy", annotation = "https://google.com"
                )
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                    append(" " + stringResource(R.string.privacy_policy_link))
                }
                pop()
            },
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

    }
}

enum class LoginType {
    EMAIL, PHONE_NUMBER
}