package com.example.auth.presentation.model

import com.example.auth.R
import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.ValidationError
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthErrorMapperTest {
    @Test
    fun maps_validation_errors_to_resources() {
        assertEquals(
            UiText.StringResource(R.string.auth_error_name_required),
            AuthError.Validation(ValidationError.NameRequired).toUiText()
        )
        assertEquals(
            UiText.StringResource(R.string.auth_error_email_required),
            AuthError.Validation(ValidationError.EmailRequired).toUiText()
        )
        assertEquals(
            UiText.StringResource(R.string.auth_error_email_invalid),
            AuthError.Validation(ValidationError.EmailInvalid).toUiText()
        )
        assertEquals(
            UiText.StringResource(R.string.auth_error_password_short),
            AuthError.Validation(ValidationError.PasswordTooShort).toUiText()
        )
    }

    @Test
    fun maps_domain_errors_to_resources() {
        assertEquals(
            UiText.StringResource(R.string.auth_error_account_not_found),
            AuthError.AccountNotFound.toUiText()
        )
        assertEquals(
            UiText.StringResource(R.string.auth_error_account_exists),
            AuthError.AccountAlreadyExists.toUiText()
        )
        assertEquals(
            UiText.StringResource(R.string.auth_error_incorrect_password),
            AuthError.IncorrectPassword.toUiText()
        )
        assertEquals(
            UiText.StringResource(R.string.auth_error_unknown),
            AuthError.Unknown.toUiText()
        )
    }
}
