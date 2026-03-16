package com.example.auth.presentation.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.auth.R
import com.example.auth.domain.usecase.RegisterUseCase
import com.example.auth.presentation.FakeAuthRepository
import com.example.auth.presentation.screen.register.RegisterRoute
import com.example.auth.presentation.viewmodel.register.RegisterViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class RegisterRouteNavigationUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun clicking_back_to_login_invokes_callback() {
        val repo = FakeAuthRepository()
        val viewModel = RegisterViewModel(RegisterUseCase(repo))
        val clicked = mutableStateOf(false)

        composeRule.setContent {
            RegisterRoute(
                onAuthSuccess = {},
                onBackToLogin = { clicked.value = true },
                viewModel = viewModel
            )
        }

        val back = composeRule.activity.getString(R.string.auth_action_back_to_login)
        composeRule.onNodeWithText(back).performClick()

        assertEquals(true, clicked.value)
    }
}
