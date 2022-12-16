package com.example.navcomponentpractice.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navcomponentpractice.R
import com.example.navcomponentpractice.data.ui.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseRecipientViewModel @Inject constructor(): ViewModel() {
    private val _recipient = MutableLiveData<String>()
    val recipient: LiveData<String> = _recipient

    private val _recipientValidationResult = MutableLiveData<ValidationResult>()
    val recipientValidationResult: LiveData<ValidationResult> = _recipientValidationResult

    fun submitRecipient(recipient: String) {
        val validate: (String) -> ValidationResult = { recipient ->
            if (recipient.length < MIN_RECIPIENT_LENGTH) {
                ValidationResult.Error(R.string.recipient_validation_length_error)
            } else {
                ValidationResult.Success
            }
        }

        val validationResult = validate(recipient)

        when(validationResult) {
            is ValidationResult.Success -> {
                _recipient.value = recipient
            }
            else -> {
                _recipient.value = ""
            }
        }

        _recipientValidationResult.value = validationResult
    }

    companion object {
        private const val MIN_RECIPIENT_LENGTH = 3
    }
}