package com.example.auth.presentation.model.forgot

import com.example.auth.presentation.model.UiText

sealed interface ForgotPasswordEffect {
    data object ResetLinkSent : ForgotPasswordEffect
    data class ShowMessage(val message: UiText) : ForgotPasswordEffect
}
