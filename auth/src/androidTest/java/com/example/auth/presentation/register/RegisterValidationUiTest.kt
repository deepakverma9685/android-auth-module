package com.example.auth.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.auth.R
import com.example.auth.domain.usecase.RegisterUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.register.RegisterRoute
import com.example.auth.presentation.viewmodel.register.RegisterViewModel
import org.junit.Rule
import org.junit.Test

class RegisterValidationUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun empty_name_shows_error_under_field() {
        val repo = FakeAuthRepository()
        val viewModel = RegisterViewModel(RegisterUseCase(repo))

        composeRule.setContent {
            RegisterRoute(
                onAuthSuccess = {},
                onBackToLogin = {},
                viewModel = viewModel
            )
        }

        val registerLabel = composeRule.activity.getString(R.string.auth_action_register)
        composeRule.onNodeWithText(registerLabel).performClick()

        val nameError = composeRule.activity.getString(R.string.auth_error_name_required)
        composeRule.onNodeWithText(nameError).assertIsDisplayed()
    }

    @Test
    fun invalid_email_does_not_show_success_snackbar() {
        val repo = FakeAuthRepository()
        val viewModel = RegisterViewModel(RegisterUseCase(repo))

        composeRule.setContent {
            RegisterRoute(
                onAuthSuccess = {},
                onBackToLogin = {},
                viewModel = viewModel
            )
        }

        val nameLabel = composeRule.activity.getString(R.string.auth_label_name)
        val emailLabel = composeRule.activity.getString(R.string.auth_label_email)
        val passwordLabel = composeRule.activity.getString(R.string.auth_label_password)
        val registerLabel = composeRule.activity.getString(R.string.auth_action_register)
        val emailError = composeRule.activity.getString(R.string.auth_error_email_invalid)
        val successText = composeRule.activity.getString(R.string.auth_success_register)

        composeRule.onNodeWithText(nameLabel).performTextInput("User")
        composeRule.onNodeWithText(emailLabel).performTextInput("invalid")
        composeRule.onNodeWithText(passwordLabel).performTextInput("password")
        composeRule.onNodeWithText(registerLabel).performClick()

        composeRule.onNodeWithText(emailError).assertIsDisplayed()
        composeRule.onAllNodesWithText(successText).assertCountEquals(0)
    }
}
