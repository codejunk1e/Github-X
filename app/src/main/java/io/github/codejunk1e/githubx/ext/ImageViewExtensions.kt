package io.github.codejunk1e.githubx.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String){
    Glide.with(this)
        .load(url)
        .apply(sharedOptions)
        .into(this)
}

var sharedOptions: RequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).timeout(6000)
