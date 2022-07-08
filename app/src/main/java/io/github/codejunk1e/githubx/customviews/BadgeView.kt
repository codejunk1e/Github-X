package io.github.codejunk1e.githubx.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import io.github.codejunk1e.githubx.R


class BadgeView: LinearLayout {
    constructor(context: Context?) : super(context) { initView() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { initView() }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) { initView() }

    private fun initView() {
        inflate(context, R.layout.badge_layout, this)
    }

    fun setCount(count: Int) {
        findViewById<TextView>(R.id.badge_count).text = count.toString()
    }
}