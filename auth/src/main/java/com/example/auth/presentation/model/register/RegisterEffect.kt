package com.example.auth.presentation.model.register

import com.example.auth.presentation.model.UiText

sealed interface RegisterEffect {
    data class Authenticated(val userId: String) : RegisterEffect
    data class ShowMessage(val message: UiText) : RegisterEffect
}
