package com.shaunhossain.phototaker.ui.take_photo

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shaunhossain.phototaker.PhotoTakerApplication
import com.shaunhossain.phototaker.databinding.FragmentTakePhotoBinding
import com.shaunhossain.phototaker.repository.TaskRepository
import com.shaunhossain.phototaker.room_db.database.AppDatabase
import com.shaunhossain.phototaker.room_db.entity.TaskImage
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TakePhotoFragment : Fragment() {

    private var _binding: FragmentTakePhotoBinding? = null
    private val binding get() = _binding!!
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    private lateinit var imageUri: Uri
    private val CAMERA_PERMISSION_CODE: Int = 1

   private val timeStamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH).format(Date())

    private val database by lazy { AppDatabase.getDataBase(requireContext()) }
    val repository by lazy { TaskRepository(database.taskImageDao()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTakePhotoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageUri = createUri()
        registerPictureLauncher()

        lifecycleScope.launch {
            repository.allTaskImage.collect {
                if(it.isNotEmpty()){
                    binding.imageView.setImageURI(null)
                    binding.imageView.setImageURI(Uri.parse(it[0].imageFilePath))
                }
            }
        }

        binding.buttonFirst.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }
    }

    private fun createUri(): Uri {
        val imageFile: File = File(requireContext().filesDir, "Take_Photo_$timeStamp.jpg")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.shaunhossain.phototaker.fileProvider",
            imageFile
        )
    }

    private fun registerPictureLauncher() {
        takePictureLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicture(),
            ActivityResultCallback { result ->
                try {
                    if (result) {
                        val taskImage: TaskImage = TaskImage(taskId = 12, ticketNumber = "ticket-13", imageFilePath = imageUri.toString(), imagePosition = 1)
                        lifecycleScope.launch {
                            repository.insertTaskImage(taskImage)
                        }
                    }
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        )
    }

    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        } else {
            takePictureLauncher.launch(imageUri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    takePictureLauncher.launch(imageUri)
                }else{
                    Toast.makeText(requireContext(),"User denied camera permission",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}