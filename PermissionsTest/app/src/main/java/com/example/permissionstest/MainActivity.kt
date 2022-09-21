package com.example.permissionstest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.permissionstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.requestPermissionOneButton.setOnClickListener {
            checkAndRequestPermission(REQUEST_CODE_CAMERA, Manifest.permission.CAMERA)
        }
        binding.requestPermissionTwoButton.setOnClickListener {
            checkAndRequestPermission(REQUEST_CODE_AUDIO, Manifest.permission.RECORD_AUDIO)
        }
        binding.requestPermissionThreeButton.setOnClickListener {
            checkAndRequestPermission(REQUEST_CODE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun checkAndRequestPermission(requestCode: Int, vararg permissions: String ){
        if (ContextCompat.checkSelfPermission(this, permissions[0]) ==
                PackageManager.PERMISSION_GRANTED){
            //while permission has been GRANTED already todo something
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(*permissions),
                requestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {}

            REQUEST_CODE_AUDIO -> {}

            REQUEST_CODE_LOCATION -> {
                // If the request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // ACCESS_FINE_LOCATION is granted
                    } else if (grantResults[1] ==
                        PackageManager.PERMISSION_GRANTED) {
                        // ACCESS_COARSE_LOCATION is granted
                    }
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    companion object{
        private const val REQUEST_CODE_CAMERA = 1
        private const val REQUEST_CODE_AUDIO = 2
        private const val REQUEST_CODE_LOCATION = 3
    }
}