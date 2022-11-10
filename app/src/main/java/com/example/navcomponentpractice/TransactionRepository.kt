package com.example.navcomponentpractice

import com.example.navcomponentpractice.data.Transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor() {

    private val savedTransactions = mutableListOf<Transaction>()

    var timesAccessed = 1
        get() { return field++ }
        private set

    fun storeTransaction(recipient: String, amount: Double): Transaction {
        val newTransaction = Transaction(recipient, amount)
        savedTransactions.add(newTransaction)

        return newTransaction
    }
}