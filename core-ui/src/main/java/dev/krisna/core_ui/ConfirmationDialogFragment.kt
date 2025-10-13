package dev.krisna.core_ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.krisna.core_ui.databinding.ConfirmationDialogFragmentBinding

class ConfirmationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = ConfirmationDialogFragmentBinding.inflate(LayoutInflater.from(requireContext()))

        // Retrieve arguments
        val title = requireArguments().getString(ARG_TITLE).orEmpty()
        val message = requireArguments().getString(ARG_MESSAGE).orEmpty()
        val positiveText = requireArguments().getString(ARG_POSITIVE_TEXT) ?: getString(android.R.string.ok)
        val negativeText = requireArguments().getString(ARG_NEGATIVE_TEXT) // Nullable
        val iconResId = requireArguments().getInt(ARG_ICON_RES, 0)
        val reqKey = requireArguments().getString(ARG_REQUEST_KEY) ?: DEFAULT_REQUEST_KEY

        // Bind data to views
        binding.title.text = title
        binding.message.text = message
        binding.positiveButton.text = positiveText

        // --- LOGIC TO HIDE/SHOW ICON ---
        if (iconResId != 0) {
            binding.icon.setImageResource(iconResId)
            binding.icon.visibility = View.VISIBLE
        } else {
            binding.icon.visibility = View.GONE
        }

        // --- LOGIC TO HIDE/SHOW NEGATIVE BUTTON ---
        if (negativeText != null) {
            binding.negativeButton.text = negativeText
            binding.negativeButton.visibility = View.VISIBLE
        } else {
            binding.negativeButton.visibility = View.GONE
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()

        // Set click listeners
        binding.positiveButton.setOnClickListener {
            setFragmentResult(reqKey, bundleOf(RESULT_CONFIRMED to true))
            dismissAllowingStateLoss()
        }
        binding.negativeButton.setOnClickListener {
            setFragmentResult(reqKey, bundleOf(RESULT_CONFIRMED to false))
            dismissAllowingStateLoss()
        }

        return dialog
    }

    companion object {
        // Add new argument key for the icon
        private const val ARG_ICON_RES = "arg_icon_res"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_MESSAGE = "arg_message"
        private const val ARG_POSITIVE_TEXT = "arg_positive_text"
        private const val ARG_NEGATIVE_TEXT = "arg_negative_text"
        private const val ARG_REQUEST_KEY = "arg_request_key"

        const val DEFAULT_REQUEST_KEY = "confirmation_dialog_result"
        const val RESULT_CONFIRMED = "confirmed"

        // Update the show method to accept an icon
        fun show(
            fm: FragmentManager,
            title: String,
            message: String,
            positiveText: String? = null,
            negativeText: String? = null,
            @DrawableRes iconResId: Int? = null,
            requestKey: String = DEFAULT_REQUEST_KEY,
            tag: String = "ConfirmationDialog"
        ) {
            val frag = ConfirmationDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_MESSAGE, message)
                    putString(ARG_POSITIVE_TEXT, positiveText)
                    putString(ARG_NEGATIVE_TEXT, negativeText) // Can be null
                    putInt(ARG_ICON_RES, iconResId ?: 0)
                    putString(ARG_REQUEST_KEY, requestKey)
                }
            }
            frag.show(fm, tag)
        }
    }
}