package dev.krisna.core_ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.krisna.core_ui.databinding.ErrorDialogFragmentBinding

class ErrorDialogFragment : DialogFragment() {

    // You might want to pass a title as well if you uncommented tv_error_title in the XML
    // For now, keeping it consistent with existing usage.

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = ErrorDialogFragmentBinding.inflate(LayoutInflater.from(requireContext()))

        val message   = requireArguments().getString(ARG_MESSAGE).orEmpty()
        val iconResId = requireArguments().getInt(ARG_ICON_RES, R.drawable.ic_alert_error)
        val reqKey    = requireArguments().getString(ARG_REQUEST_KEY) ?: DEFAULT_REQUEST_KEY

        // Bind data
        binding.tvErrorDescription.text = message

        // Optional: If you add a title argument, you can set it here
        // val title = requireArguments().getString(ARG_TITLE)
        // if (title != null) {
        //     binding.tvErrorTitle.text = title
        //     binding.tvErrorTitle.visibility = View.VISIBLE
        // }

        if (iconResId != 0) binding.ivError.setImageResource(iconResId)

        // Handle OK button click
        binding.btnOk.setOnClickListener { // CHANGED: Now listening on btn_ok
            setFragmentResult(reqKey, bundleOf(RESULT_ACKNOWLEDGED to true))
            dismissAllowingStateLoss()
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()

        // You might want to disallow canceling on touch outside for critical errors,
        // but for general errors, it's often user-friendly to allow it.
        // dialog.setCanceledOnTouchOutside(false) // Consider this for critical errors
        // isCancelable = false // Consider this for critical errors

        return dialog
    }

    override fun onDismiss(dialog: android.content.DialogInterface) {
        super.onDismiss(dialog)
        // Ensure a result is emitted even if dismissed via back/outside or implicitly
        val reqKey = requireArguments().getString(ARG_REQUEST_KEY) ?: DEFAULT_REQUEST_KEY
        setFragmentResult(reqKey, bundleOf(RESULT_ACKNOWLEDGED to true))
    }

    companion object {
        private const val ARG_MESSAGE = "arg_message"
        private const val ARG_ICON_RES = "arg_icon_res"
        // private const val ARG_TITLE = "arg_title" // Uncomment if you add a title
        private const val ARG_REQUEST_KEY = "arg_request_key"

        const val DEFAULT_REQUEST_KEY = "error_dialog_result"
        const val RESULT_ACKNOWLEDGED = "acknowledged"

        fun show(
            fm: FragmentManager,
            message: String,
            iconResId: Int = R.drawable.ic_alert_error,
            // title: String? = null, // Uncomment if you add a title
            requestKey: String = DEFAULT_REQUEST_KEY,
            tag: String = "ErrorDialog"
        ) {
            ErrorDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MESSAGE, message)
                    putInt(ARG_ICON_RES, iconResId)
                    // putString(ARG_TITLE, title) // Uncomment if you add a title
                    putString(ARG_REQUEST_KEY, requestKey)
                }
            }.show(fm, tag)
        }
    }
}