package com.example.alertdialogtest

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.alertdialogtest.databinding.ActivityAlertDialogLevelOneBinding
import com.example.alertdialogtest.entities.AvailableVolumeValues
import kotlin.properties.Delegates.notNull

class AlertDialogLevelOneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlertDialogLevelOneBinding
    private var volume by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog_level_one)

    }

    private fun updateUi(){
        binding.textViewCurrentVolume.text = getString(R.string.current_volume)
        binding.colorView.setBackgroundColor(color)
    }

    private fun showAlertDialog(){

        val alertDialogListener = DialogInterface.OnClickListener { dialog, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> Toast.makeText(this,
                    "You have pressed -Yes-",
                    Toast.LENGTH_SHORT).show()

                DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(this,
                    "You have pressed -No-",
                    Toast.LENGTH_SHORT).show()

                DialogInterface.BUTTON_NEUTRAL -> Toast.makeText(this,
                    "Ignored",
                    Toast.LENGTH_SHORT).show()
            }
        }

        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(R.string.alert_dialog_default_button)
            .setMessage(R.string.default_alert_dialog_message)
            .setPositiveButton("Yes", alertDialogListener)
            .setNegativeButton("No", alertDialogListener)
            .setNeutralButton("Ignore", alertDialogListener)
            .setOnCancelListener{
                Toast.makeText(this, "Dialog cancelled", Toast.LENGTH_SHORT).show()
            }
            .create()
        dialog.show()
    }

    private fun showSingleChoiceAlertDialog(){
        val volumeItems: AvailableVolumeValues = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems: Array<String> = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Volume setup")
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex){ dialog, which ->
                volume = volumeItems.values[which]
                updateUi()
                dialog.dismiss()
            }
            .create()
        dialog.show()

    }

    private fun showSingleChoiceWithConfirmationAlertDialog(){
        val volumeItems: AvailableVolumeValues = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems: Array<String> = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Volume setup")
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex, null)
            .setPositiveButton("Confirm"){dialog, _ ->
                val index: Int = (dialog as AlertDialog).listView.checkedItemPosition
                volume = volumeItems.values[index]
            }
            .create()
        dialog.show()
    }
}