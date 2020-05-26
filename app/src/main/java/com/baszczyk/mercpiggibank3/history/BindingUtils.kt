package com.baszczyk.mercpiggibank3.history

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.baszczyk.mercpiggibank3.database.entities.Deposit

@BindingAdapter("depositDurationFormatted")
fun TextView.setDepositDurationFormatted(item: Deposit) {
    item?.let {
        text = item.data
    }
}

@BindingAdapter("depositQualityString")
fun TextView.setDepositQualityString(item: Deposit) {
    item?.let {
        text = item.amount.toString()
    }
}

@BindingAdapter("mercedesQualityString")
fun TextView.setMercedesQualityString(item: Deposit) {
    item?.let {
        text = item.piggyName
    }
}