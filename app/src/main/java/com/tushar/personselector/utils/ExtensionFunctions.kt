package com.tushar.personselector.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * To Show a view
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * To Hide a view
 */
fun View.hide() {
    visibility = View.GONE
}

/**
 * Can show [Toast] from every [Activity].
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}