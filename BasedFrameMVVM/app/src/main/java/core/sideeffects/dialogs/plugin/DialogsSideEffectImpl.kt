package core.sideeffects.dialogs.plugin

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import core.model.SuccessResult
import core.sideeffects.SideEffectImplementation

class DialogsSideEffectImpl(
    private val retainedState: DialogsSideEffectMediator.RetainedState
) : SideEffectImplementation(), LifecycleObserver {

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        val record = retainedState.record ?: return
        showDialog(record)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        removeDialog()
    }

    fun showDialog(record: DialogsSideEffectMediator.DialogRecord) {
        val config = record.config
        val emitter = record.emitter
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(config.title)
            .setMessage(config.message)
            .setCancelable(config.cancellable)
        if (config.positiveButton.isNotBlank()) {
            builder.setPositiveButton(config.positiveButton) { _, _ ->
                emitter.emit(SuccessResult(true))
                dialog = null
            }
        }
        if (config.negativeButton.isNotBlank()) {
            builder.setNegativeButton(config.negativeButton) { _, _ ->
                emitter.emit(SuccessResult(false))
                dialog = null
            }
        }
        if (config.cancellable) {
            builder.setOnCancelListener {
                emitter.emit(SuccessResult(false))
                dialog = null
            }
        }
        val dialog = builder.create()
        dialog.show()
        this.dialog = dialog
    }

    fun removeDialog() {
        dialog?.dismiss()
        dialog = null
    }


}