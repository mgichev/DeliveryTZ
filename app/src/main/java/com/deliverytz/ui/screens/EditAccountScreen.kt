package com.deliverytz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deliverytz.R
import com.deliverytz.domain.LoginType
import com.deliverytz.domain.MAX_TEXT_FIELD_LENGTH
import com.deliverytz.domain.User


/**
 * Отображает для окна DetailScreen TextField   элемент с заданным modifier.
 * По умолчанию растягивается по ширине parent.
 * Параметры функции соответствуют параметрам TextField
 */
@Composable
fun DetailItem(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isEnabled: Boolean
) {
    TextField(modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            if (it.length < MAX_TEXT_FIELD_LENGTH) onValueChanged(it)
        },
        placeholder = { placeholder?.let { Text(placeholder) } },
        enabled = isEnabled,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = { Text(label) })
}


/**
 * Отображает элементы Text и TextField в контейнере Column
 * @param modifier modifier параметр контейнера
 */

@Composable
fun DetailItemWithText(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    itemText: String = "Text",
    isEnable: Boolean = false,
) {

    Column(modifier = modifier.width(IntrinsicSize.Min), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = itemText,
        )
        TextField(
            onValueChange = {
                if (it.length < MAX_TEXT_FIELD_LENGTH) onValueChanged(it)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.secondary,
            ),
            value = value,
            enabled = isEnable,
        )
    }
}

/**
 * Содержимое экрана EditAccountScreen
 */
@Composable
fun AccountDetails(
    modifier: Modifier = Modifier,
    isModeEditable: Boolean,
    onSaveConfirm: (user: User) -> Unit,
    onEditModeChange: () -> Unit,
    onUnauthAccountClick: () -> Unit,
    onRemoveAccountClicked: () -> Unit,
    user: User?
) {

    var phoneNumber by rememberSaveable { mutableStateOf(user?.phone ?: "") }
    var email by rememberSaveable { mutableStateOf(user?.email ?: "") }
    var userName by rememberSaveable { mutableStateOf(user?.name ?: "") }
    var birthday by rememberSaveable { mutableStateOf(user?.birthday ?: "") }
    var deliveryAddress by rememberSaveable { mutableStateOf(user?.address ?: "") }
    var apartment by rememberSaveable { mutableStateOf(user?.apartment ?: "") }
    var entrance by rememberSaveable { mutableStateOf(user?.entrance ?: "") }
    var floor by rememberSaveable { mutableStateOf(user?.floor ?: "") }
    var apartmentNumber by rememberSaveable { mutableStateOf(user?.apartmentPhone ?: "") }

    var showRemoveDialog by rememberSaveable { mutableStateOf(false) }
    var showExitDialog by rememberSaveable { mutableStateOf(false) }

    if (showRemoveDialog) {
        SimpleDialog(
            modifier = modifier,
            title = stringResource(R.string.remove_account),
            confirmText = stringResource(R.string.remove_account_confirm),
            yBtnText = stringResource(R.string.yes_remove),
            onDismissClick = {
                showRemoveDialog = false
            },
            onConfirmClick = {
                showRemoveDialog = false
                onRemoveAccountClicked()
            }
        )
    }

    if (showExitDialog) {
        SimpleDialog(
            modifier = modifier,
            title = stringResource(R.string.logout_from_account),
            confirmText = stringResource(R.string.logout_from_account_confirm),
            yBtnText = stringResource(R.string.yes_unauth),
            onDismissClick = {
                showExitDialog = false
            },
            onConfirmClick = {
                showExitDialog = false
                onUnauthAccountClick()
            }
        )
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DetailItem(
            value = phoneNumber,
            onValueChanged = {
                phoneNumber = it
            },
            label = stringResource(R.string.phone_number_text),
            placeholder = stringResource(R.string.phone_number_hint),
            isEnabled = isModeEditable && (user?.phone?.isBlank() == true),
            keyboardType = KeyboardType.Phone
        )
        DetailItem(
            value = email,
            onValueChanged = { email = it },
            label = stringResource(R.string.email_text),
            isEnabled = isModeEditable && (user?.email?.isBlank() == true),
            keyboardType = KeyboardType.Email
        )
        DetailItem(
            value = userName,
            onValueChanged = { userName = it },
            label = stringResource(R.string.name),
            isEnabled = isModeEditable
        )
        DetailItem(
            value = birthday,
            onValueChanged = { birthday = it },
            label = stringResource(R.string.date_birthday_text),
            isEnabled = isModeEditable,
        )
        Text(
            text = stringResource(R.string.delivery_address_large),
            style = MaterialTheme.typography.headlineSmall
        )
        DetailItem(
            value = deliveryAddress,
            onValueChanged = { deliveryAddress = it },
            label = stringResource(R.string.delivery_address_text),
            isEnabled = isModeEditable
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailItemWithText(
                value = apartment,
                onValueChanged = { apartment = it },
                Modifier.weight(1f),
                itemText = stringResource(R.string.delivery_address_apartment),
                isEnable = isModeEditable
            )
            DetailItemWithText(
                value = entrance,
                onValueChanged = { entrance = it },
                Modifier.weight(1f),
                itemText = stringResource(R.string.delivery_address_entrance),
                isEnable = isModeEditable
            )
            DetailItemWithText(
                value = floor,
                onValueChanged = { floor = it },
                Modifier.weight(1f),
                itemText = stringResource(R.string.delivery_address_floor),
                isEnable = isModeEditable
            )
            DetailItemWithText(
                value = apartmentNumber,
                onValueChanged = { apartmentNumber = it },
                Modifier.weight(1f),
                itemText = stringResource(R.string.delivery_address_apartment_number),
                isEnable = isModeEditable
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (isModeEditable) {
            Button(
                modifier = modifier.fillMaxWidth(),
                onClick = {
                    onSaveConfirm(User(
                        name = userName,
                        phone = phoneNumber,
                        email = email,
                        birthday = birthday,
                        loginType = if(email.isBlank()) LoginType.PHONE_NUMBER else LoginType.EMAIL,
                        address = deliveryAddress,
                        apartment = apartment,
                        entrance = entrance,
                        floor = floor,
                        apartmentPhone = apartmentNumber
                    ))

                    onEditModeChange()

                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.save_changes_btn),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {

                TextButton(
                    onClick = {
                        showRemoveDialog = true
                    }
                ) {

                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        text = stringResource(R.string.remove_btn_text)
                    )
                }

                TextButton(
                    onClick = {
                        showExitDialog = true
                    }
                ) {

                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        text = stringResource(R.string.logout_btn_text)
                    )
                }

            }

        }


    }
}

/**
 * Экран EditScren
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountScreen(
    isEditMode: Boolean = false,
    onRemoveAccountClicked: () -> Unit,
    onUnauthAccountClick: () -> Unit,
    onBackBtnClicked: () -> Unit,
    onSaveConfirm: (user: User) -> Unit,
    user: User?,
) {
    var isEditableMode by rememberSaveable { mutableStateOf(isEditMode) }
    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.account_data)) }, navigationIcon = {
            IconButton(
                onClick = onBackBtnClicked
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }, actions = {
            if (!isEditableMode) {
                IconButton(onClick = {
                    isEditableMode = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.Create, contentDescription = null
                    )
                }
            }
        })
    }) {
        AccountDetails(
            modifier = Modifier.padding(it),
            isModeEditable = isEditableMode,
            onSaveConfirm = onSaveConfirm,
            onEditModeChange = {
                isEditableMode = !isEditableMode
            },
            user = user,
            onRemoveAccountClicked = onRemoveAccountClicked,
            onUnauthAccountClick = onUnauthAccountClick
        )
    }
}


/**
 * Показывает простой диалог (заголовок, описание, кнопки выхода)
 * @param title Заголовок
 * @param confirmText Описание
 * @param yBtnText Текст кнопки подтверждения
 * @param onDismissClick callback выхода из диалога
 * @param onConfirmClick callback подтверждения диалога
 */
@Composable
fun SimpleDialog(
    modifier: Modifier = Modifier,
    title: String,
    confirmText: String,
    yBtnText: String,
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        title = { Text( text = title)},
        text = {
            Text( text = confirmText)
        },
        onDismissRequest = {
            onDismissClick()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmClick()
            }) {
                Text(text = yBtnText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissClick()
                }
            ) {
                Text(text = stringResource(R.string.undo))
            }
        }
    )
}

@Preview
@Composable
fun ShowDetailScreen() {
    EditAccountScreen(false, {}, {}, {}, {},null)
}