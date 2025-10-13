package dev.krisna.core_ui

import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager

class DialogNavigatorImpl(
    private val fragmentManager: FragmentManager
) : DialogNavigator {

    override fun showConfirmation(
        title: String,
        message: String,
        positiveText: String?,
        negativeText: String?,
        requestKey: String,
        tag: String
    ) {
        ConfirmationDialogFragment.show(
            fm = fragmentManager,
            title = title,
            message = message,
            positiveText = positiveText,
            negativeText = negativeText,
            requestKey = requestKey,
            tag = tag
        )
    }

    override fun showError(
        message: String,
        @DrawableRes iconResId: Int?,
        requestKey: String,
        tag: String
    ) {
        // Use the default icon from ErrorDialogFragment if null is passed.
        // This keeps the default logic within the implementation and the interface clean.
        val actualIconResId = iconResId ?: R.drawable.ic_alert_error

        ErrorDialogFragment.show(
            fm = fragmentManager,
            message = message,
            iconResId = actualIconResId,
            requestKey = requestKey,
            tag = tag
        )
    }
}