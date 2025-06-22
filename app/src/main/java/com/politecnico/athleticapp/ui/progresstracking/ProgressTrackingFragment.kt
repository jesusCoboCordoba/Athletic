package com.politecnico.athleticapp.ui.progresstracking

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.politecnico.athleticapp.MainActivity
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentProgressTrackingBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID


class ProgressTrackingFragment : Fragment() {

    private var _binding: FragmentProgressTrackingBinding? = null
    private val binding get() = _binding!!

    private val database = Firebase.database
    private val progressRef = database.getReference("progress-tracking")
    private val storageRef = Firebase.storage.reference

    private var currentPhotoUri: Uri? = null

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            dispatchTakePictureIntent()
        } else {
            Toast.makeText(context, "Camera permission is required to take a photo.", Toast.LENGTH_SHORT).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            currentPhotoUri?.let { uri ->
                binding.addPhotoButton.text = "Photo Selected (Click to change)"
            }
        } else {
            Toast.makeText(context, "Failed to take photo.", Toast.LENGTH_SHORT).show()
            currentPhotoUri = null
            binding.addPhotoButton.text = "Add Photo"
        }
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            currentPhotoUri = it
            binding.addPhotoButton.text = "Photo Selected (Click to change)"

        } ?: run {
            Toast.makeText(context, "No photo selected.", Toast.LENGTH_SHORT).show()
            currentPhotoUri = null
            binding.addPhotoButton.text = "Add Photo"
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.hideLoading()

        binding.addPhotoButton.setOnClickListener {
            showImageSourceSelectionDialog()
        }

        binding.saveButton.setOnClickListener {
            saveProgressEntry()
        }

        binding.viewGraphButton.setOnClickListener {
            Toast.makeText(context, "Navigating to Graph View!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_nav_progress_tracking_to_nav_progress_detail)
        }
    }

    private fun showImageSourceSelectionDialog() {
        val options = arrayOf("Take Photo", "Choose from Gallery", "Cancel")
        AlertDialog.Builder(requireContext())
            .setTitle("Add Photo")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> // Take Photo
                        checkCameraPermissionAndTakePicture()
                    1 -> // Choose from Gallery
                        pickImageLauncher.launch("image/*")
                    2 -> // Cancel
                        dialog.dismiss()
                }
            }
            .show()
    }

    private fun checkCameraPermissionAndTakePicture() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                dispatchTakePictureIntent()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Camera Permission Needed")
                    .setMessage("This app needs camera access to take photos of your progress.")
                    .setPositiveButton("OK") { _, _ ->
                        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
            else -> {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: Exception) {
            Toast.makeText(context, "Error creating image file: ${ex.message}", Toast.LENGTH_LONG).show()
            null
        }
        photoFile?.also {
            currentPhotoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                it
            )
            takePictureLauncher.launch(currentPhotoUri)
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun saveProgressEntry() {
        val weight = binding.weightEditText.text.toString().trim()
        val measurement1 = binding.measurementEditText1.text.toString().trim()
        val measurement2 = binding.measurementEditText2.text.toString().trim()

        if (weight.isNotBlank() && measurement1.isNotBlank() && measurement2.isNotBlank()) {
            Toast.makeText(context, "Saving progress...", Toast.LENGTH_SHORT).show()

            if (currentPhotoUri != null) {
                uploadImageToFirebaseStorage(currentPhotoUri!!) { imageUrl ->
                    val newEntry = ProgressEntry(
                        weight = weight,
                        measurement1 = measurement1,
                        measurement2 = measurement2,
                        timestamp = System.currentTimeMillis(),
                        imageUrl = imageUrl
                    )
                    saveEntryToDatabase(newEntry)
                }
            } else {
                val newEntry = ProgressEntry(
                    weight = weight,
                    measurement1 = measurement1,
                    measurement2 = measurement2,
                    timestamp = System.currentTimeMillis(),
                    imageUrl = null
                )
                saveEntryToDatabase(newEntry)
            }
        } else {
            Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri, onComplete: (String?) -> Unit) {
        val fileName = "${UUID.randomUUID()}.jpg"
        val imageRef = storageRef.child("progress_images/$fileName")

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    onComplete(uri.toString())
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to get download URL: ${it.message}", Toast.LENGTH_LONG).show()
                    onComplete(null)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to upload image: ${it.message}", Toast.LENGTH_LONG).show()
                onComplete(null)
            }
    }

    private fun saveEntryToDatabase(entry: ProgressEntry) {
        val newEntryRef = progressRef.push()
        entry.id = newEntryRef.key!!
        newEntryRef.setValue(entry)
            .addOnSuccessListener {
                Toast.makeText(context, "Progress saved successfully!", Toast.LENGTH_SHORT).show()
                binding.weightEditText.text.clear()
                binding.measurementEditText1.text.clear()
                binding.measurementEditText2.text.clear()
                currentPhotoUri = null
                binding.addPhotoButton.text = "Add Photo"
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to save progress data: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}