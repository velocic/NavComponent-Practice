package com.example.navcomponentpractice.ui

import androidx.lifecycle.ViewModel
import com.example.navcomponentpractice.TransactionRepository
import com.example.navcomponentpractice.data.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewTransactionsViewModel @Inject constructor(
    val transactionRepository: TransactionRepository
): ViewModel() {
    var timesReused = 0
    val transactions = listOf(
        Transaction("Adama", 200.25),
        Transaction("Starbuck", 3.50),
        Transaction("Flynne", 25.25),
        Transaction("Burton", 55.55),
        Transaction("Corbell", 10000.00)
    )
}