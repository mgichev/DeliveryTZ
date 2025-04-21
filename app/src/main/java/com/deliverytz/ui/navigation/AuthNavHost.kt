package com.deliverytz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deliverytz.ui.screens.AccountScreen
import com.deliverytz.ui.screens.CodeScreen
import com.deliverytz.ui.screens.EditAccountScreen
import com.deliverytz.ui.screens.LoginScreen
import com.deliverytz.ui.screens.UserViewModel

@Composable
fun AuthNavHost() {

    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    NavHost(navController, startDestination = Route.AccountScreen.route) {

        composable(Route.CodeScreen.route) {
            CodeScreen(
                onBackBtnClicked = {
                    userViewModel.logout()
                    navController.popBackStack()
                },
                user = userViewModel.user.value,
                onNextBtnClicked = {
                    userViewModel.fetchData()
                    navController.navigate(Route.AccountScreen.route)
                }
            )
        }
        composable(Route.AccountScreen.route) {
            AccountScreen(
                user = userViewModel.user.value,
                onAuthCardClicked = { navController.navigate(Route.DetailScreen.route) },
                onUnauthCardClick = { navController.navigate(Route.LoginScreen.route) }
            )
        }
        composable(Route.LoginScreen.route) {
            LoginScreen(
                onNextBtnClick = { email, phone ->
                    userViewModel.tryLoginUser(phone, email)
                    navController.navigate(Route.CodeScreen.route)
                },
                onSkipBtnClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Route.DetailScreen.route) {
            userViewModel.fetchData()
            EditAccountScreen(
                user = userViewModel.user.value,
                onBackBtnClicked = { navController.popBackStack() },
                onSaveConfirm = { user ->
                    userViewModel.saveData(user)
                },
                onRemoveAccountClicked = {
                    navController.popBackStack(Route.LoginScreen.route, inclusive = true)
                    userViewModel.removeAccount()
                },
                onUnauthAccountClick = {
                    navController.popBackStack(Route.LoginScreen.route, inclusive = true)
                    userViewModel.logout()
                }
            )
        }
    }
}