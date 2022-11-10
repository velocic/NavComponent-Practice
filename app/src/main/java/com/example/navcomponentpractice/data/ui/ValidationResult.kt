package com.example.navcomponentpractice.data.ui

import androidx.annotation.StringRes

sealed class ValidationResult {
    object Success: ValidationResult()
    class Error(
        @StringRes val errorMessageResId: Int,
        vararg val messageArgs: Any
    ): ValidationResult()
}