package com.shaunhossain.phototaker.ui.dialog

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.shaunhossain.phototaker.databinding.ContentImagePreviewBinding


class ContentImagePreviewDialog : DialogFragment() {

    private var _binding: ContentImagePreviewBinding? = null
    private val binding get() = _binding!!
    private val args: ContentImagePreviewDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ContentImagePreviewBinding.inflate(inflater, container, false)
        binding.imageView.setImageURI(Uri.parse(args.taskData.imageFilePath))
        return binding.root
    }
}