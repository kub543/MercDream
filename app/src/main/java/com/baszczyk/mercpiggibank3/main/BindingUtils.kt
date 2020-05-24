package com.baszczyk.mercpiggibank3.main

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank

@BindingAdapter("piggyDurationFormatted")
fun TextView.setPiggyDurationFormatted(item: PiggyBank) {
    item?.let {
        text = item.piggyName
    }
}


@BindingAdapter("piggyQualityString")
fun TextView.setPiggyQualityString(item: PiggyBank) {
    item?.let {
        text = item.actualAmount.toString()
    }
}