package com.example.auth.presentation.forgot

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.auth.R
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.ForgotPasswordUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.forgot.ForgotPasswordRoute
import com.example.auth.presentation.viewmodel.forgot.ForgotPasswordViewModel
import org.junit.Rule
import org.junit.Test

class ForgotPasswordScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun forgot_password_shows_success_snackbar() {
        val repo = FakeAuthRepository().apply {
            resetResult = AuthResult.Success("user-3")
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
        val successText = composeRule.activity.getString(R.string.auth_success_reset_sent)

        composeRule.onNodeWithText(emailLabel).performTextInput("user@test.com")
        composeRule.onNodeWithText(actionLabel).performClick()

        composeRule.onNodeWithText(successText).assertIsDisplayed()
    }
}
