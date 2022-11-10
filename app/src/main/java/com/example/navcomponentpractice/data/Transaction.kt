package com.example.navcomponentpractice.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val recipientName: String,
    val amount: Double
): Parcelable