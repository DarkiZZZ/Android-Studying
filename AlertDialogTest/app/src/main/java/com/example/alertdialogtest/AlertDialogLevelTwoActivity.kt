package com.example.alertdialogtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.alertdialogtest.databinding.ActivityAlertDialogLevelOneBinding
import com.example.alertdialogtest.databinding.PartVolumeBinding
import com.example.alertdialogtest.entities.AvailableVolumeValues
import kotlin.properties.Delegates


private const val KEY_VOLUME = "keyVolume2"

class AlertDialogLevelTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlertDialogLevelOneBinding
    private var volume by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog_level_two)
    }

    private fun updateUi(){

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
    }

    private fun showCustomAlertDialog(){
        val dialogBinding = PartVolumeBinding.inflate(layoutInflater)
        dialogBinding.volumeSeekBar.progress = volume
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle("Volume Setup")
            .setMessage("Please, specify desired volume")
            .setView(dialogBinding.root)
            .setPositiveButton("Confirm"){_, _ ->
                volume = dialogBinding.volumeSeekBar.progress
                updateUi()
            }
            .create()
        dialog.show()
    }

    private fun showCustomSingleChoiceAlertDialog(){
        val volumeItems: AvailableVolumeValues = AvailableVolumeValues.createVolumeValues(volume)
        val adapter = VolumeAdapter(volumeItems.values)

        var volume: Int = this.volume
        var dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Volume setup")
            .setSingleChoiceItems(adapter, volumeItems.currentIndex){_, which ->
                volume = adapter.getItem(which)
            }
            .setPositiveButton("Confirm"){_, _ ->
                this.volume = volume
                updateUi()
            }
            .create()
        dialog.show()

    }
}