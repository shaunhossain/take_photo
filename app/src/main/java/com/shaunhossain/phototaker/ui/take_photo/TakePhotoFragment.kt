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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.shaunhossain.phototaker.databinding.FragmentTakePhotoBinding
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

        Log.d("imageUri","${imageUri.path}")

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
                        binding.imageView.setImageURI(null)
                        binding.imageView.setImageURI(imageUri)
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