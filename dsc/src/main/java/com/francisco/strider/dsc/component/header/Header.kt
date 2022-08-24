package com.francisco.strider.dsc.component.header

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.francisco.strider.dsc.R
import com.francisco.strider.dsc.component.icon.IconTextView
import com.francisco.strider.dsc.extensions.findView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.progressindicator.LinearProgressIndicator

class Header @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.style.Widget_MaterialComponents_Toolbar_Primary
) : MaterialToolbar(context, attrs, defStyleAttr) {

    private val clContainer: ConstraintLayout by findView(R.id.cl_container)

    private val tvTitle: TextView by findView(R.id.tv_title)

    private val itvIconLeft: IconTextView by findView(R.id.itv_icon_left)

    private val itvIconRight: IconTextView by findView(R.id.itv_icon_right)

    private val lpiProgress: LinearProgressIndicator by findView(R.id.lpi_progress)

    init {
        View.inflate(context, R.layout.header_view, this)
        removeInsets()
        setIconRippleColor(ContextCompat.getColor(context, R.color.support300))
    }

    fun setTitle(title: String) = tvTitle.run {
        text = title
        visibility = VISIBLE
    }

    fun setIconLeft(fontIcon: String) {
        itvIconLeft.text = fontIcon
    }

    fun setIconLeftClick(onClick: () -> Unit) = itvIconLeft.run {
        setOnClickListener { onClick() }
        visibility = VISIBLE
    }

    fun setIconRight(fontIcon: String) {
        itvIconRight.text = fontIcon
    }

    fun setIconRightClick(onClick: () -> Unit) = itvIconRight.run {
        setOnClickListener { onClick() }
        visibility = VISIBLE
    }

    fun setIconRightVisibility(visible: Boolean) {
        itvIconRight.isInvisible = !visible
    }

    fun setIconLeftVisibility(visible: Boolean) {
        itvIconLeft.isInvisible = !visible
    }

    fun setPrimaryColor(@ColorInt color: Int) {
        clContainer.setBackgroundColor(color)
    }

    fun setOnPrimaryColor(@ColorInt color: Int) {
        tvTitle.setTextColor(color)
        itvIconLeft.setTextColor(color)
        itvIconRight.setTextColor(color)
    }

    fun setIconRippleColor(@ColorInt rippleColor: Int) {
        setIconRippleColor(itvIconLeft, rippleColor)
        setIconRippleColor(itvIconRight, rippleColor)
    }

    fun setProgressColor(
        @ColorInt indicatorColor: Int,
        @ColorInt trackColor: Int
    ) = lpiProgress.run {
        setIndicatorColor(indicatorColor)
        setTrackColor(trackColor)
    }

    fun setProgressMax(max: Int) = lpiProgress.run {
        progress = ZERO
        isVisible = true
        setMax(max)
    }

    fun setProgressVisibility(visible: Boolean) = lpiProgress.run {
        isVisible = visible
    }

    fun setProgress(
        progress: Int,
        animated: Boolean = true
    ) {
        lpiProgress.setProgressCompat(progress, animated)
    }

    fun getProgress() = lpiProgress.progress

    private fun setIconRippleColor(
        iconTextView: IconTextView,
        @ColorInt rippleColor: Int
    ) = iconTextView.run {
        background = (background as RippleDrawable).apply {
            setColor(ColorStateList.valueOf(rippleColor))
        }
    }

    private fun removeInsets() {
        setContentInsetsAbsolute(ZERO, ZERO)
        setContentInsetsRelative(ZERO, ZERO)
    }

    private companion object {
        const val ZERO = 0
    }

}