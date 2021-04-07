package info.firozansari.moviemvp.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import info.firozansari.moviemvp.R
import info.firozansari.moviemvp.core.ConfigVariables.BASE_IMAGE_ADDRESS

fun ImageView.setImagePath(path: String) {
    Glide.with(context).apply {
        load("${BASE_IMAGE_ADDRESS}$path")
            .error(R.drawable.not_found)
            .into(this@setImagePath)
    }
}