package com.example.auth.presentation.screen.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.R
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.model.login.LoginEffect
import com.example.auth.presentation.model.login.LoginIntent
import com.example.auth.presentation.screen.components.AuthOutlinedTextField
import com.example.auth.presentation.screen.components.AuthPrimaryButton
import com.example.auth.presentation.screen.components.AuthScaffold
import com.example.auth.presentation.screen.components.SocialDivider
import com.example.auth.presentation.screen.components.SocialLoginRow
import com.example.auth.presentation.viewmodel.login.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    onAuthSuccess: (userId: String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleLogin: () -> Unit = {},
    onFacebookLogin: () -> Unit = {},
    onMessage: (message: String) -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var infoMessage by remember { mutableStateOf<UiText?>(null) }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is LoginEffect.Authenticated -> onAuthSuccess(effect.userId)
                is LoginEffect.ShowMessage -> {
                    infoMessage = effect.message
                    onMessage(effect.message.asString(context))
                }
            }
        }
    }

    LaunchedEffect(infoMessage) {
        if (infoMessage != null) {
            delay(2200)
            infoMessage = null
        }
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically { it / 6 },
        exit = fadeOut() + slideOutVertically { it / 6 }
    ) {
        AuthScaffold(
            modifier = modifier,
            titleRes = R.string.auth_title_login,
            subtitleRes = R.string.auth_subtitle_login,
            infoMessage = infoMessage
        ) {
            AuthOutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onIntent(LoginIntent.UpdateEmail(it)) },
                labelRes = R.string.auth_label_email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                error = state.emailError
            )
            Spacer(modifier = Modifier.height(12.dp))
            AuthOutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onIntent(LoginIntent.UpdatePassword(it)) },
                labelRes = R.string.auth_label_password,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                error = state.passwordError
            )
            Spacer(modifier = Modifier.height(20.dp))
            AuthPrimaryButton(
                textRes = R.string.auth_action_login,
                isLoading = state.isLoading,
                onClick = { viewModel.onIntent(LoginIntent.Submit) }
            )
            if (state.generalError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = state.generalError!!.asString(context))
            }

            Spacer(modifier = Modifier.height(12.dp))
            SocialDivider()
            SocialLoginRow(
                onGoogleClick = onGoogleLogin,
                onFacebookClick = onFacebookLogin
            )

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = onForgotPasswordClick,
                colors = ButtonDefaults.textButtonColors()
            ) {
                Text(stringResource(R.string.auth_action_forgot_password))
            }
            TextButton(
                onClick = onRegisterClick,
                colors = ButtonDefaults.textButtonColors()
            ) {
                Text(stringResource(R.string.auth_action_create_account))
            }
        }
    }
}
