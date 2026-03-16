package com.example.auth.presentation.model

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class StringResource(@StringRes val resId: Int, val args: List<Any> = emptyList()) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is Dynamic -> value
            is StringResource -> context.getString(resId, *args.toTypedArray())
        }
    }
}
