package com.example.auth.presentation.forgot

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.auth.R
import com.example.auth.domain.model.AuthError
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.ForgotPasswordUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.forgot.ForgotPasswordRoute
import com.example.auth.presentation.viewmodel.forgot.ForgotPasswordViewModel
import org.junit.Rule
import org.junit.Test

class ForgotPasswordErrorMappingUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun account_not_found_shows_email_error() {
        val repo = FakeAuthRepository().apply {
            resetResult = AuthResult.Error(AuthError.AccountNotFound)
        }
        val viewModel = ForgotPasswordViewModel(ForgotPasswordUseCase(repo))

        composeRule.setContent {
            ForgotPasswordRoute(
                onResetLinkSent = {},
                onBackToLogin = {},
                viewModel = viewModel
            )
        }

        val emailLabel = composeRule.activity.getString(R.string.auth_label_email)
        val actionLabel = composeRule.activity.getString(R.string.auth_action_send_reset)
        val errorText = composeRule.activity.getString(R.string.auth_error_account_not_found)

        composeRule.onNodeWithText(emailLabel).performTextInput("user@test.com")
        composeRule.onNodeWithText(actionLabel).performClick()

        composeRule.onNodeWithText(errorText).assertIsDisplayed()
    }
}
