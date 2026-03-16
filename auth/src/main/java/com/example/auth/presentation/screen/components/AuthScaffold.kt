package com.example.auth.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.annotation.StringRes
import com.example.auth.presentation.model.UiText
import com.example.auth.presentation.screen.components.style.AuthColors
import com.example.auth.R

@Composable
fun AuthScaffold(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    @StringRes subtitleRes: Int,
    infoMessage: UiText? = null,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContextProvider.current


    LaunchedEffect(infoMessage) {
        if (infoMessage != null) {
            snackbarHostState.showSnackbar(infoMessage.asString(context))
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent,
        contentColor = Color.Unspecified
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(AuthColors.BackgroundBrush)
        ) {
            AuthBackgroundDecor()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(16.dp, shape = MaterialTheme.shapes.extraLarge),
                    shape = MaterialTheme.shapes.extraLarge,
                    tonalElevation = 6.dp,
                    color = AuthColors.SurfaceTint
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = stringResource(titleRes),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = AuthColors.Title
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(subtitleRes),
                            style = MaterialTheme.typography.bodyMedium,
                            color = AuthColors.Subtitle
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                        content()
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = stringResource(R.string.auth_footer_security),
                    style = MaterialTheme.typography.labelMedium,
                    color = AuthColors.Footer
                )
            }
        }
    }
}

private object LocalContextProvider {
    val current: android.content.Context
        @Composable get() = androidx.compose.ui.platform.LocalContext.current
}
