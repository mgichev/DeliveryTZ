package com.deliverytz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        AccountCard(accountCardText = stringResource(R.string.login_text_btn))
        SectionCard(sectionText = stringResource(R.string.orders_history))
        SectionCard(sectionText = stringResource(R.string.personal_data))
        SectionCard(sectionText = stringResource(R.string.use_rules))
        VersionText()
    }
}

@Composable
fun AccountCard(modifier: Modifier = Modifier, accountCardText: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.AccountCircle,
                contentDescription = null,
                modifier = modifier.requiredSize(64.dp)
            )
            Text(
                text = accountCardText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1
            )
            Spacer(modifier = modifier.weight(1f))
            IconButton(onClick = { }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight, null,
                    modifier = Modifier.requiredSize(32.dp)
                )
            }
        }
    }
}


@Composable
fun SectionCard(modifier: Modifier = Modifier, sectionText: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = sectionText,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight, null,
                    modifier = Modifier.requiredSize(32.dp)
                )
            }
        }
    }
}

@Composable
fun VersionText(modifier: Modifier = Modifier, version: String = "1.0.0") {
    Text(
        modifier = modifier,
        text = stringResource(R.string.app_version, version),
        style = MaterialTheme.typography.labelLarge
    )
}