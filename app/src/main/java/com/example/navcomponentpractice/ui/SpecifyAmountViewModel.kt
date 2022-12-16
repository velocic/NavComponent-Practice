package com.example.navcomponentpractice.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navcomponentpractice.R
import com.example.navcomponentpractice.TransactionRepository
import com.example.navcomponentpractice.data.Transaction
import com.example.navcomponentpractice.data.ui.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecifyAmountViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
): ViewModel() {
    val recipientName = MutableLiveData<String>()

    private val _amount = MutableStateFlow(0.0)
    val amount: StateFlow<Double> = _amount

    private val _amountValidationResult = MutableSharedFlow<ValidationResult>()
    val amountValidationResult: SharedFlow<ValidationResult> = _amountValidationResult

    private val _completedTransaction = MutableSharedFlow<Transaction>()
    val completedTransaction: SharedFlow<Transaction> = _completedTransaction

    fun submitAmount(amount: Double) {
        val validate: (Double) -> ValidationResult = { amount ->
            if (amount <= 0) {
                ValidationResult.Error(R.string.negative_amount_error)
            } else {
                ValidationResult.Success
            }
        }

        val validationResult = validate(amount)

        when(validationResult) {
            is ValidationResult.Success -> {
                _amount.value = amount

                viewModelScope.launch {
                    _completedTransaction.emit(
                        transactionRepository.storeTransaction(recipientName.value!!, amount)
                    )
                }
            }
            else -> {
                _amount.value = 0.0
            }
        }

        viewModelScope.launch {
            _amountValidationResult.emit(validationResult)
        }
    }
}