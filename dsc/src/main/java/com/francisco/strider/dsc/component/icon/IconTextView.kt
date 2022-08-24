package com.francisco.strider.dsc.component.icon

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.FontRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.francisco.strider.dsc.R

class IconTextView(
    context: Context,
    attrs: AttributeSet?
) : AppCompatTextView(
    context,
    attrs
) {
    init {
        typeface = ResourcesCompat.getFont(context, R.font.fontawesomekit_regular)
    }

    fun setText(text: String, @FontRes id: Int) {
        this.text = text
        this.typeface = ResourcesCompat.getFont(context, id)
    }
}
