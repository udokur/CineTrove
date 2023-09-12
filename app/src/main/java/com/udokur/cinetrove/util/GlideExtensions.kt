package com.udokur.cinetrove.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import com.udokur.cinetrove.R


fun ImageView.loadImage(path: String?) {

    Glide.with(this.context).load(Constant.IMAGE_BASE_URL+path).apply(centerCropTransform())
        .into(this)


}