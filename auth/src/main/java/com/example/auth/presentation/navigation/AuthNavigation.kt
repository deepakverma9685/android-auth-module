package com.example.auth.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.auth.presentation.screen.forgot.ForgotPasswordRoute
import com.example.auth.presentation.screen.login.LoginRoute
import com.example.auth.presentation.screen.register.RegisterRoute

object AuthDestinations {
    const val Login = "auth/login"
    const val Register = "auth/register"
    const val ForgotPassword = "auth/forgot"

    const val DeepLinkLogin = "auth://login"
    const val DeepLinkRegister = "auth://register"
    const val DeepLinkForgot = "auth://forgot"
}

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = AuthDestinations.Login,
    onAuthSuccess: (userId: String) -> Unit = {},
    onPasswordResetSent: () -> Unit = {},
    onMessage: (message: String) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = AuthDestinations.Login,
            deepLinks = listOf(navDeepLink { uriPattern = AuthDestinations.DeepLinkLogin })
        ) {
            LoginRoute(
                onAuthSuccess = onAuthSuccess,
                onForgotPasswordClick = { navController.navigate(AuthDestinations.ForgotPassword) },
                onRegisterClick = {
                    Log.e("TAG", "AuthNavGraph:-------- ", )
                    navController.navigate(AuthDestinations.Register) },
                onMessage = onMessage
            )
        }
        composable(
            route = AuthDestinations.Register,
            deepLinks = listOf(navDeepLink { uriPattern = AuthDestinations.DeepLinkRegister })
        ) {
            RegisterRoute(
                onAuthSuccess = onAuthSuccess,
                onBackToLogin = { navController.navigate(AuthDestinations.Login) },
                onMessage = onMessage
            )
        }
        composable(
            route = AuthDestinations.ForgotPassword,
            deepLinks = listOf(navDeepLink { uriPattern = AuthDestinations.DeepLinkForgot })
        ) {
            ForgotPasswordRoute(
                onResetLinkSent = onPasswordResetSent,
                onBackToLogin = { navController.navigate(AuthDestinations.Login) },
                onMessage = onMessage
            )
        }
    }
}
