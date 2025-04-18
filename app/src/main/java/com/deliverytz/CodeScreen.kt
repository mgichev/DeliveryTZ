package com.deliverytz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deliverytz.ui.theme.CodeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CodeScreen(modifier: Modifier = Modifier, loginType: LoginType = LoginType.EMAIL) {
    Scaffold(topBar = {
        TopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        })
    }) {
        GetCode(Modifier.padding(it), loginType)
    }
}

@Composable
fun GetCode(modifier: Modifier = Modifier, loginType: LoginType = LoginType.EMAIL) {

    val codeViewModel: CodeViewModel = viewModel()

    val code = codeViewModel._code.observeAsState()
    var codeInput by rememberSaveable { mutableStateOf("") }
    var isCodeTimerWork by rememberSaveable { mutableStateOf(true) }
    var loginStatus by rememberSaveable { mutableStateOf(LoginStatus.FIRST_ATTEMPT) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = modifier.weight(3f))
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
                R.string.code_is_send_to_email,
                "abcd"
            ) else stringResource(
                R.string.code_is_send_to_number, "1234"
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            value = codeInput,
            onValueChange = { codeInput = it },
        )
        Spacer(Modifier.weight(1f))
        if (loginStatus != LoginStatus.FIRST_ATTEMPT)
            Text(text = stringResource(R.string.wrong_code),
                textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        else
            Spacer(Modifier.weight(1f))
        if (!isCodeTimerWork)
            TextButton(onClick = {

        }) {
            Text(
                text = stringResource(R.string.resend_code),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            Text(
                text = stringResource(R.string.resend_code_time, 60),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = modifier.weight(2f))
        Button(
            onClick = { if (codeInput != code.value) loginStatus = LoginStatus.ERROR},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = stringResource(R.string.to_next),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(
            text = if (loginType == LoginType.EMAIL) stringResource(R.string.not_get_email) else stringResource(R.string.not_get_phone),
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

    }
}

enum class LoginStatus {
    FIRST_ATTEMPT, ERROR, SUCCESS
}