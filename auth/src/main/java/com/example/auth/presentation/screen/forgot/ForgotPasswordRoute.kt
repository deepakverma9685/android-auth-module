package com.example.auth.presentation.screen.forgot

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.R
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.model.forgot.ForgotPasswordEffect
import com.example.auth.presentation.model.forgot.ForgotPasswordIntent
import com.example.auth.presentation.screen.components.AuthOutlinedTextField
import com.example.auth.presentation.screen.components.AuthPrimaryButton
import com.example.auth.presentation.screen.components.AuthScaffold
import com.example.auth.presentation.viewmodel.forgot.ForgotPasswordViewModel
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordRoute(
    modifier: Modifier = Modifier,
    onResetLinkSent: () -> Unit,
    onBackToLogin: () -> Unit,
    onMessage: (message: String) -> Unit = {},
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var infoMessage by remember { mutableStateOf<UiText?>(null) }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ForgotPasswordEffect.ResetLinkSent -> onResetLinkSent()
                is ForgotPasswordEffect.ShowMessage -> {
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
            titleRes = R.string.auth_title_forgot,
            subtitleRes = R.string.auth_subtitle_forgot,
            infoMessage = infoMessage
        ) {
            AuthOutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.onIntent(ForgotPasswordIntent.UpdateEmail(it)) },
                labelRes = R.string.auth_label_email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                error = state.emailError
            )
            Spacer(modifier = Modifier.height(20.dp))
            AuthPrimaryButton(
                textRes = R.string.auth_action_send_reset,
                isLoading = state.isLoading,
                onClick = { viewModel.onIntent(ForgotPasswordIntent.Submit) }
            )
            if (state.generalError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = state.generalError!!.asString(context))
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = onBackToLogin,
                colors = ButtonDefaults.textButtonColors()
            ) {
                Text(stringResource(R.string.auth_action_back_to_login))
            }
        }
    }
}
