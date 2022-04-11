package info.firozansari.movieapp.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import info.firozansari.movieapp.R
import info.firozansari.movieapp.presentation.Config.BASE_IMAGE_ADDRESS

fun ImageView.setImagePath(path: String) {
    Glide.with(context).apply {
        load("${BASE_IMAGE_ADDRESS}$path")
            .error(R.drawable.not_found)
            .into(this@setImagePath)
    }
}