package com.example.auth.presentation.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.auth.R
import com.example.auth.presentation.screen.components.style.AuthColors

@Composable
fun SocialLoginRow(
    modifier: Modifier = Modifier,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        SocialButton(
            textRes = R.string.auth_social_google,
            onClick = onGoogleClick,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        SocialButton(
            textRes = R.string.auth_social_facebook,
            onClick = onFacebookClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SocialDivider(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.auth_social_divider),
        color = AuthColors.Subtitle,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
private fun SocialButton(
    textRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF334C46))
    ) {
        Text(text = stringResource(textRes))
    }
}
