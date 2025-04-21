package com.deliverytz.ui.screens

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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.deliverytz.R
import com.deliverytz.domain.User


/**
 * Экран данных аккаунта.
 * @param onAuthCardClicked вызывается при нажатии на карточку с именем,
 * когда пользователь авторизован
 * @param onUnauthCardClick вызывается при нажатии на карточку с именем,
 * когда пользователь не авторизован
 */
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    user: User? = null,
    onAuthCardClicked: () -> Unit = {},
    onUnauthCardClick: () -> Unit = {},
) {


    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (user == null) LoginCard(
            accountCardText = stringResource(R.string.login_text_btn),
            onCardClick = onUnauthCardClick
        )
        else AuthorizedCard(
            onCardClicked = onAuthCardClicked,
            username = user.name
        )
        SectionCard(sectionText = stringResource(R.string.orders_history))
        SectionCard(sectionText = stringResource(R.string.personal_data))
        SectionCard(sectionText = stringResource(R.string.use_rules))
        VersionText()
    }
}

/**
 * Отображает карточку для не залогиненных пользователей
 * @param accountCardText текст карточки
 * @param onCardClick вызывается при нажатии на карточку и кнопку
 */
@Composable
fun LoginCard(modifier: Modifier = Modifier, accountCardText: String, onCardClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer
        ), onClick = { onCardClick() }, modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                Icons.Rounded.AccountCircle,
                contentDescription = null,
                modifier = modifier.requiredSize(64.dp),
                tint = MaterialTheme.colorScheme.tertiary
            )

            Text(
                text = accountCardText, style = MaterialTheme.typography.bodyLarge, maxLines = 1
            )
            Spacer(modifier = modifier.weight(1f))
            IconButton(onClick = { onCardClick() }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    null,
                    modifier = Modifier.requiredSize(32.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}


/**
 * Отображает карточку для авторизованных пользователей
 * @param username имя пользователя, используется для отображения изображения
 * @param onCardClicked вызывается при нажатии на карточку и кнопку
 */
@Composable
fun AuthorizedCard(
    modifier: Modifier = Modifier, username: String = "Name", onCardClicked: () -> Unit
) {

    val colorImage = MaterialTheme.colorScheme.tertiary
    val imageSize = 60f

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer
        ), onClick = { onCardClicked() }, modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = colorImage, radius = imageSize
                        )
                    }, text = username[0].toString(), style = MaterialTheme.typography.headlineSmall
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = username, style = MaterialTheme.typography.bodyLarge, maxLines = 1
                )

                Text(
                    text = stringResource(R.string.change_data),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                    maxLines = 1
                )
            }

            Spacer(modifier = modifier.weight(1f))
            IconButton(onClick = { onCardClicked() }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    null,
                    modifier = Modifier.requiredSize(32.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

/**
 * Стандартная карточка с текстом и кнопкой
 * @param sectionText текст на карточке
 */

@Composable
fun SectionCard(modifier: Modifier = Modifier, sectionText: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        // для эффекта нажатия на карточку
        onClick = {}, modifier = modifier.fillMaxWidth()
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
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    null,
                    modifier = Modifier.requiredSize(32.dp),
                    tint = MaterialTheme.colorScheme.tertiary
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