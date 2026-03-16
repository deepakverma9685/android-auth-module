package com.example.auth.presentation.register

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.auth.R
import com.example.auth.domain.model.AuthResult
import com.example.auth.domain.usecase.RegisterUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.register.RegisterRoute
import com.example.auth.presentation.viewmodel.register.RegisterViewModel
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun register_shows_success_snackbar() {
        val repo = FakeAuthRepository().apply {
            registerResult = AuthResult.Success("user-2")
        }
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
        val successText = composeRule.activity.getString(R.string.auth_success_register)

        composeRule.onNodeWithText(nameLabel).performTextInput("User")
        composeRule.onNodeWithText(emailLabel).performTextInput("user@test.com")
        composeRule.onNodeWithText(passwordLabel).performTextInput("password")
        composeRule.onNodeWithText(registerLabel).performClick()

        composeRule.onNodeWithText(successText).assertIsDisplayed()
    }
}
