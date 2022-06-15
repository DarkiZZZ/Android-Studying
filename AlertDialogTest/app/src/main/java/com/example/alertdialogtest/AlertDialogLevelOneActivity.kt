package com.example.alertdialogtest

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.alertdialogtest.databinding.ActivityAlertDialogLevelOneBinding

class AlertDialogLevelOneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlertDialogLevelOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog_level_one)
    }

    fun updateUi(){

    }

    private fun showAlertDialog(){

        val alertDialogListener = DialogInterface.OnClickListener { dialog, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> Toast.makeText(this, "You have pressed -Yes-",
                    Toast.LENGTH_SHORT).show()
                DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(this, "You have pressed -No-",
                    Toast.LENGTH_SHORT).show()
                DialogInterface.BUTTON_NEUTRAL -> Toast.makeText(this, "Ignored", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "Dialog dismissed", Toast.LENGTH_SHORT).show()
            }
            .create()
        dialog.show()
    }
}