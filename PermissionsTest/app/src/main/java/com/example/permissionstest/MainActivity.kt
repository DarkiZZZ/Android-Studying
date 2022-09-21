package com.example.permissionstest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.permissionstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("TAG", "onCreate started")

        binding.requestPermissionOneButton.setOnClickListener {
            Log.d("TAG", "Camera request")
            checkAndRequestPermission(REQUEST_CODE_CAMERA, Manifest.permission.CAMERA)
        }

        binding.requestPermissionTwoButton.setOnClickListener {
            Log.d("TAG", "Audio request")
            checkAndRequestPermission(REQUEST_CODE_AUDIO, Manifest.permission.RECORD_AUDIO)
        }

        binding.requestPermissionThreeButton.setOnClickListener {
            Log.d("TAG", "Location request")
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
                showToast(R.string.permission_granted)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQUEST_CODE_LOCATION
                )
            }
        }

        binding.testButton.setOnClickListener {
            Log.d("TAG", "test")
            Toast.makeText(this, "Test!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    onPermissionGrantedMessageShow(REQUEST_CODE_CAMERA)
                }
                else{
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        askUserForOpeningAppSettings()
                    } else{
                        showToast(R.string.permission_denied_message)
                    }
                }
            }

            REQUEST_CODE_AUDIO -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    onPermissionGrantedMessageShow(REQUEST_CODE_AUDIO)
                }
                else{
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)){
                        askUserForOpeningAppSettings()
                    } else{
                        showToast(R.string.permission_denied_message)
                    }
                }
            }

            REQUEST_CODE_LOCATION -> {
                Log.d("TAG", "grantResult are NOT empty")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showToast(R.string.fine_location_permission_granted_message)
                } else if (grantResults[1] ==
                    PackageManager.PERMISSION_GRANTED) {
                        showToast(R.string.coarse_location_permission_granted_message)
                } else{
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) &&
                        !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                        askUserForOpeningAppSettings()
                    } else{
                        showToast(R.string.permission_denied_message)
                    }
                }
            }
        }
    }

    private fun checkAndRequestPermission(requestCode: Int, vararg permissions: String ){
        if (ContextCompat.checkSelfPermission(this, permissions[0]) ==
            PackageManager.PERMISSION_GRANTED){
            showToast(R.string.permission_granted)
        } else {
            ActivityCompat.requestPermissions(
                this,
                permissions.toList().toTypedArray(),
                requestCode
            )
        }
    }

    private fun onPermissionGrantedMessageShow(requestCode: Int){
        when(requestCode){
            REQUEST_CODE_CAMERA -> showToast(R.string.camera_permission_granted)
            REQUEST_CODE_AUDIO ->  showToast(R.string.audio_permission_granted)
            REQUEST_CODE_LOCATION -> showToast(R.string.location_permission_granted)
        }
    }

    private fun showToast(message: Int){
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
    }

    private fun askUserForOpeningAppSettings(){
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        if (packageManager.resolveActivity(appSettingsIntent, PackageManager.MATCH_DEFAULT_ONLY) == null){
            Toast.makeText(this, getString(R.string.permission_denied_forever_message), Toast.LENGTH_SHORT).show()
        }
        else{
            AlertDialog.Builder(this)
                .setTitle("Permissions denied")
                .setMessage("You have denied permissions forever." +
                            "You can change your decision in app settings.\n\n" +
                            "Would you like to open app settings?")
                .setPositiveButton("Open"){_, _ ->
                    startActivity(appSettingsIntent)
                }
                .setNegativeButton("Close"){dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }

    companion object{
        private const val REQUEST_CODE_CAMERA = 1
        private const val REQUEST_CODE_AUDIO = 2
        private const val REQUEST_CODE_LOCATION = 3
    }
}