package com.example.navcomponentpractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navcomponentpractice.data.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(): ViewModel() {
    val lastCompletedTransaction = MutableLiveData<Transaction>()
}