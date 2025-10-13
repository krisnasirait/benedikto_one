package dev.krisna.core_ui

import androidx.annotation.DrawableRes

interface DialogNavigator {

    /**
     * Shows a confirmation dialog with positive and negative actions.
     *
     * @param title The title of the dialog.
     * @param message The main content/message of the dialog.
     * @param positiveText The text for the positive button. Defaults to "OK".
     * @param negativeText The text for the negative button. Defaults to "Cancel".
     * @param requestKey A key to identify the result from this dialog.
     * @param tag A tag for the fragment transaction.
     */
    fun showConfirmation(
        title: String,
        message: String,
        positiveText: String? = null,
        negativeText: String? = null,
        requestKey: String = ConfirmationDialogFragment.DEFAULT_REQUEST_KEY,
        tag: String = "ConfirmationDialog"
    )

    /**
     * Shows an error dialog.
     *
     * @param message The error message to display.
     * @param iconResId An optional drawable resource for the error icon. Defaults to a standard error icon.
     * @param requestKey A key to identify the result from this dialog (when acknowledged).
     * @param tag A tag for the fragment transaction.
     */
    fun showError(
        message: String,
        @DrawableRes iconResId: Int? = null,
        requestKey: String = ErrorDialogFragment.DEFAULT_REQUEST_KEY,
        tag: String = "ErrorDialog"
    )
}