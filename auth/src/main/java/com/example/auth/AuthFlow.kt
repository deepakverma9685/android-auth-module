package com.example.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.auth.presentation.model.AuthScreen
import com.example.auth.presentation.screen.forgot.ForgotPasswordRoute
import com.example.auth.presentation.screen.login.LoginRoute
import com.example.auth.presentation.screen.register.RegisterRoute

@Composable
fun AuthFlow(
    modifier: Modifier = Modifier,
    initialScreen: AuthScreen = AuthScreen.Login,
    onAuthSuccess: (userId: String) -> Unit = {},
    onPasswordResetSent: () -> Unit = {},
    onGoogleLogin: () -> Unit = {},
    onFacebookLogin: () -> Unit = {},
    onMessage: (message: String) -> Unit = {}
) {
    var screen by rememberSaveable { mutableStateOf(initialScreen) }

    when (screen) {
        AuthScreen.Login -> LoginRoute(
            modifier = modifier,
            onAuthSuccess = onAuthSuccess,
            onForgotPasswordClick = { screen = AuthScreen.ForgotPassword },
            onRegisterClick = { screen = AuthScreen.Register },
            onGoogleLogin = onGoogleLogin,
            onFacebookLogin = onFacebookLogin,
            onMessage = onMessage
        )
        AuthScreen.Register -> RegisterRoute(
            modifier = modifier,
            onAuthSuccess = onAuthSuccess,
            onBackToLogin = { screen = AuthScreen.Login },
            onGoogleLogin = onGoogleLogin,
            onFacebookLogin = onFacebookLogin,
            onMessage = onMessage
        )
        AuthScreen.ForgotPassword -> ForgotPasswordRoute(
            modifier = modifier,
            onResetLinkSent = onPasswordResetSent,
            onBackToLogin = { screen = AuthScreen.Login },
            onMessage = onMessage
        )
    }
}
