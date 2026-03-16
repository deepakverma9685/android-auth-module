package com.example.auth.presentation.model

import com.example.auth.R
import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.ValidationError

fun AuthError.toUiText(): UiText {
    return when (this) {
        is AuthError.Validation -> validationErrorToUiText(error)
        AuthError.AccountNotFound -> UiText.StringResource(R.string.auth_error_account_not_found)
        AuthError.AccountAlreadyExists -> UiText.StringResource(R.string.auth_error_account_exists)
        AuthError.IncorrectPassword -> UiText.StringResource(R.string.auth_error_incorrect_password)
        AuthError.Unknown -> UiText.StringResource(R.string.auth_error_unknown)
    }
}

private fun validationErrorToUiText(error: ValidationError): UiText {
    return when (error) {
        ValidationError.NameRequired -> UiText.StringResource(R.string.auth_error_name_required)
        ValidationError.EmailRequired -> UiText.StringResource(R.string.auth_error_email_required)
        ValidationError.EmailInvalid -> UiText.StringResource(R.string.auth_error_email_invalid)
        ValidationError.PasswordTooShort -> UiText.StringResource(R.string.auth_error_password_short)
    }
}
