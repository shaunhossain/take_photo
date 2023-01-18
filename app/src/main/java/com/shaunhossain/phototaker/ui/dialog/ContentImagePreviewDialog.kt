package com.shaunhossain.phototaker.ui.dialog

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shaunhossain.phototaker.databinding.ContentImagePreviewBinding
import com.shaunhossain.phototaker.repository.TaskRepository
import com.shaunhossain.phototaker.room_db.database.AppDatabase
import kotlinx.coroutines.launch


class ContentImagePreviewDialog : DialogFragment() {

    private var _binding: ContentImagePreviewBinding? = null
    private val binding get() = _binding!!
    private val args: ContentImagePreviewDialogArgs by navArgs()

    private val database by lazy { AppDatabase.getDataBase(requireContext()) }
    private val repository by lazy { TaskRepository(database.taskImageDao()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ContentImagePreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setImageURI(Uri.parse(args.taskData.imageFilePath))
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.deleteButton.setOnClickListener {

            repository.deleteFile(Uri.parse(args.taskData.imageFilePath),requireContext())

            lifecycleScope.launch {
                repository.deleteTaskImage(args.taskData)
            }
            findNavController().popBackStack()
        }
    }
}