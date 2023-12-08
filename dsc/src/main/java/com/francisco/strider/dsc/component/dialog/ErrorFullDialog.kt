package com.francisco.strider.dsc.component.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.francisco.strider.dsc.R
import com.francisco.strider.dsc.component.dialog.args.ErrorFullDialogArgs
import com.francisco.strider.dsc.component.header.Header
import com.francisco.strider.dsc.component.icon.IconTextView
import com.francisco.strider.dsc.extensions.setCustomText
import com.francisco.strider.dsc.extensions.toHtml
import com.google.android.material.button.MaterialButton

class ErrorFullDialog : BaseDialogFragment() {

    var args = ErrorFullDialogArgs()

    var rootView: View? = null
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.error_full_view, container).also { rootView = it }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DscTheme_Dialog_FullScreen_Error)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyDialogState(savedInstanceState)
        isCancelable = args.cancelable
        setupHeader()
        setupIcon()
        setupTitle()
        setupMessage()
        setupBottomButton()
    }

    /**
     * If any config changes occur dialog must be dismissed to evict memory problems
     */
    private fun verifyDialogState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) dismissAllowingStateLoss()
    }

    private fun setupHeader() = rootView?.findViewById<Header>(R.id.header)?.run {
        setPrimaryColor(ContextCompat.getColor(context, R.color.primary400))
        setOnPrimaryColor(ContextCompat.getColor(context, R.color.support100))
        setIconRippleColor(ContextCompat.getColor(context, R.color.primary100))
        setIconRightClick {
            dismiss()
            args.onCloseClick.invoke()
        }
    }

    private fun setupIcon() = rootView?.findViewById<IconTextView>(R.id.itv_icon)?.run {
        text = args.icon.ifEmpty { context.getString(R.string.icon_tired_kit) }
    }

    private fun setupTitle() = rootView?.findViewById<TextView>(R.id.tv_error_title)?.run {
        text = args.title.toHtml()
        isVisible = args.title.isNotEmpty()
    }

    private fun setupMessage() = rootView?.findViewById<TextView>(R.id.tv_error_message)?.run {
        setCustomText(args.message, R.font.avertastd_bold)
        isVisible = args.message.isNotEmpty()
    }

    private fun setupBottomButton() = rootView?.findViewById<MaterialButton>(R.id.bt_bottom)?.run {
        text = args.bottomButtonText
        isVisible = args.bottomButtonText.isNotEmpty()
        setOnClickListener {
            dismiss()
            args.onBottomButtonClick.invoke()
        }
    }

}
