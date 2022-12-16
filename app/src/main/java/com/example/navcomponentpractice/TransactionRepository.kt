package com.example.navcomponentpractice

import com.example.navcomponentpractice.data.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor() {

    private val _savedTransactions = mutableListOf<Transaction>()
    val savedTransactions = _savedTransactions.asFlow()

    var timesAccessed = 1
        get() { return field++ }
        private set

    suspend fun storeTransaction(recipient: String, amount: Double): Transaction {
        val newTransaction = Transaction(recipient, amount)

        _savedTransactions.add(newTransaction)
        //TODO: How to publish to savedTransactions? Can't seem to emit on a cold flow as
        //this function gets new transactions

        return newTransaction
    }
}