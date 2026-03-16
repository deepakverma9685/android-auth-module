package com.example.auth.presentation.forgot

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.auth.R
import com.example.auth.domain.usecase.ForgotPasswordUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.forgot.ForgotPasswordRoute
import com.example.auth.presentation.viewmodel.forgot.ForgotPasswordViewModel
import org.junit.Rule
import org.junit.Test

class ForgotPasswordValidationUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun empty_email_shows_error_under_field() {
        val repo = FakeAuthRepository()
        val viewModel = ForgotPasswordViewModel(ForgotPasswordUseCase(repo))

        composeRule.setContent {
            ForgotPasswordRoute(
                onResetLinkSent = {},
                onBackToLogin = {},
                viewModel = viewModel
            )
        }

        val actionLabel = composeRule.activity.getString(R.string.auth_action_send_reset)
        composeRule.onNodeWithText(actionLabel).performClick()

        val emailError = composeRule.activity.getString(R.string.auth_error_email_required)
        composeRule.onNodeWithText(emailError).assertIsDisplayed()
    }

    @Test
    fun invalid_email_does_not_show_success_snackbar() {
        val repo = FakeAuthRepository()
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
        val emailError = composeRule.activity.getString(R.string.auth_error_email_invalid)
        val successText = composeRule.activity.getString(R.string.auth_success_reset_sent)

        composeRule.onNodeWithText(emailLabel).performTextInput("invalid")
        composeRule.onNodeWithText(actionLabel).performClick()

        composeRule.onNodeWithText(emailError).assertIsDisplayed()
        composeRule.onAllNodesWithText(successText).assertCountEquals(0)
    }
}
