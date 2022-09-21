package com.albertsons.assignment.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View.hideKeyboard() {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        windowToken?.let {
            hideSoftInputFromWindow(it, 0)
        }
    }
}