package com.example.client.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.makeGone() {
    visibility = GONE
}

fun View.makeVisible() {
    visibility = VISIBLE
}
