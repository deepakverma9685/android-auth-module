package com.example.auth.presentation.model.login

import com.example.auth.presentation.model.UiText

sealed interface LoginEffect {
    data class Authenticated(val userId: String) : LoginEffect
    data class ShowMessage(val message: UiText) : LoginEffect
}
