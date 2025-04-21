package com.deliverytz.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.deliverytz.ui.navigation.AuthNavHost
import com.deliverytz.ui.screens.AccountScreen
import com.deliverytz.ui.screens.CodeScreen
import com.deliverytz.ui.screens.EditAccountScreen
import com.deliverytz.ui.screens.LoginScreen
import com.deliverytz.ui.theme.DeliveryTZTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeliveryTZTheme(darkTheme = true) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AuthNavHost()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    DeliveryTZTheme {
        LoginScreen(onNextBtnClick = {_, _ -> }, onSkipBtnClick = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCodeScreen() {
    DeliveryTZTheme {
        CodeScreen(
            user = null,
            onBackBtnClicked = {},
            onNextBtnClicked = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAccountScreen() {
    DeliveryTZTheme {
        AccountScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAccountDetailScreen() {
    DeliveryTZTheme {
        EditAccountScreen(false, { }, {}, {}, {}, null)
    }
}


