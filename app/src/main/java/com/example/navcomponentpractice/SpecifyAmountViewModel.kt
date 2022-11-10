package com.example.navcomponentpractice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navcomponentpractice.data.Transaction
import com.example.navcomponentpractice.data.ui.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpecifyAmountViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
): ViewModel() {
    val recipientName = MutableLiveData<String>()

    private val _amount = MutableLiveData<Double>()
    val amount: LiveData<Double> = _amount

    private val _amountValidationResult = MutableLiveData<ValidationResult>()
    val amountValidationResult: LiveData<ValidationResult> = _amountValidationResult

    private val _completedTransaction = MutableLiveData<Transaction>()
    val completedTransaction: LiveData<Transaction> = _completedTransaction

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
                _completedTransaction.value = transactionRepository.storeTransaction(
                    recipientName.value!!,
                    amount
                )
            }
            else -> {
                _amount.value = 0.0
            }
        }

        _amountValidationResult.value = validationResult
    }
}