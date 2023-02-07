package ru.msokolov.cameraxtest

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.msokolov.cameraxtest.databinding.ActivityMainBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDir: File
    private var cameraImageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            takePhoto()
        }
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                PERMISSION_LIST,
                PERMISSION_CODE
            )
        }
        outputDir = getOutputDir()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun takePhoto() {
        val imageCapture = cameraImageCapture ?: return
        val photoFile = File(
            outputDir,
            SimpleDateFormat(FILE_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = Uri.fromFile(photoFile)
                    val msg = "Photo saved: $uri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(baseContext, "Error: ${exception.message}", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun getOutputDir(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.absoluteFile.let {
            File(it, resources.getString(R.string.app_name)).apply {
                mkdir()
            }
        }
        return if (mediaDir.exists() && mediaDir != null) {
            mediaDir
        } else {
            filesDir
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                    .also {
                        it.setSurfaceProvider(binding.previewView.surfaceProvider)
                    }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraImageCapture = ImageCapture.Builder().build()
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        cameraImageCapture
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Camera provider bind error", e)
                }
            }, ContextCompat.getMainExecutor(this)
        )
    }

    private fun allPermissionsGranted(): Boolean {
        return PERMISSION_LIST.all {
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private const val TAG = "CameraX"
        private const val FILE_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PERMISSION_CODE = 10
        private val PERMISSION_LIST = arrayOf(android.Manifest.permission.CAMERA)
    }
}