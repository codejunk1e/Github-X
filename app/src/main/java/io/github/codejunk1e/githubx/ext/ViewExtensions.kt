package io.github.codejunk1e.githubx.ext

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide(toInvisible: Boolean = true) {
    this.visibility = if (toInvisible) View.INVISIBLE else View.GONE
}