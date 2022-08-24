package com.francisco.strider.dsc.component.dialog

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialogFragment : DialogFragment() {

    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun dismiss() = dismissAllowingStateLoss()

    override fun dismissAllowingStateLoss() {
        val ft = fragmentManager?.beginTransaction()
        ft?.remove(this)
        ft?.commitAllowingStateLoss()
    }
}