package com.deliverytz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    label: String = "Enter text",
    placeholder: String? = null,
    isEnabled: Boolean
) {

    var text by remember { mutableStateOf("") }

    TextField(modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it},
        placeholder = { placeholder?.let { Text(placeholder) } },
        enabled = isEnabled,
        label = { Text(label) })
}

@Composable
fun SmallDetailItemWithText(
    modifier: Modifier = Modifier, itemText: String = "Text", isEnable: Boolean
) {

    var text by remember { mutableStateOf("") }

    Column(modifier = modifier.width(IntrinsicSize.Min)) {
        Text(
            text = itemText,
        )
        TextField(modifier = Modifier.width(80.dp), onValueChange = {text = it}, value = text, enabled = isEnable)
    }
}

@Composable
fun AccountDetails(modifier: Modifier = Modifier, isModeEditable: Boolean) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DetailItem(
            label = stringResource(R.string.phone_number_text),
            placeholder = stringResource(R.string.phone_number_hint),
            isEnabled = isModeEditable
        )
        DetailItem(label = stringResource(R.string.email_text),
            isEnabled = isModeEditable
        )
        DetailItem(label = stringResource(R.string.name),
            isEnabled = isModeEditable
        )
        DetailItem(label = stringResource(R.string.date_birthday_text),
            isEnabled = isModeEditable
        )
        Text(text = stringResource(R.string.delivery_address_large))
        DetailItem(label = stringResource(R.string.delivery_address_text),
            isEnabled = isModeEditable
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SmallDetailItemWithText(
                itemText = stringResource(R.string.delivery_address_apartment),
                isEnable = isModeEditable
            )
            SmallDetailItemWithText(
                itemText = stringResource(R.string.delivery_address_entrance),
                isEnable = isModeEditable
            )
            SmallDetailItemWithText(
                itemText = stringResource(R.string.delivery_address_floor),
                isEnable = isModeEditable
            )
            SmallDetailItemWithText(
                itemText = stringResource(R.string.delivery_address_apartment_number),
                isEnable = isModeEditable
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(modifier = modifier.fillMaxWidth(), onClick = {}) {
            Text(text = stringResource(R.string.save_changes_btn))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(_isEditableMode: Boolean = false) {
    var isEditableMode by rememberSaveable() { mutableStateOf(_isEditableMode) }
    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.account_data)) }, navigationIcon = {
            IconButton(onClick = {}) {
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
        AccountDetails(Modifier.padding(it), isEditableMode)
    }
}

@Preview
@Composable
fun ShowDetailScreen() {
    DetailScreen(_isEditableMode = true)
}