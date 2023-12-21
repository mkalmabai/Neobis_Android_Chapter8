package com.example.mobimarket.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mobimarket.R
import com.google.android.material.snackbar.Snackbar

class showSnackBar {
    companion object {
        fun showCustomSnackbar(
            context: Context,
            rootView: View,
            message: String
        ) {
            val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
            val params = snackbar.view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            snackbar.view.layoutParams = params
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                .setTextColor(ContextCompat.getColor(context, R.color.white))
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                .setPadding((25 * context.resources.displayMetrics.density).toInt(),0, 0,0,)
            snackbar.view.background = ContextCompat.getDrawable(context, R.drawable.snackbar)
            snackbar.show()
        }
    }
}