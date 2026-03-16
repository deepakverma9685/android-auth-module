package com.example.auth.presentation.model

import androidx.test.core.app.ApplicationProvider
import com.example.auth.R
import org.junit.Assert.assertEquals
import org.junit.Test

class UiTextTest {
    @Test
    fun string_resource_resolves_correctly() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val text = UiText.StringResource(R.string.auth_success_login)
        assertEquals(context.getString(R.string.auth_success_login), text.asString(context))
    }

    @Test
    fun dynamic_text_returns_value() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val text = UiText.Dynamic("Hello")
        assertEquals("Hello", text.asString(context))
    }
}
