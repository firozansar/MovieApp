package info.firozansari.movieapp.presentation.util

import android.view.View

fun View.show(display: Boolean) {
    visibility = if (display) View.VISIBLE else View.GONE
}
