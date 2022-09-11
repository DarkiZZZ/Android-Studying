package com.example.basedframemvvm.views.currentcolor

import android.Manifest.*
import com.example.basedframemvvm.R
import com.example.basedframemvvm.model.colors.ColorListener
import com.example.basedframemvvm.model.colors.ColorsRepository
import com.example.basedframemvvm.model.colors.NamedColor
import core.sideeffects.navigator.Navigator
import core.views.BaseViewModel
import com.example.basedframemvvm.views.changecolor.ChangeColorFragment
import core.model.PendingResult
import core.model.SuccessResult
import core.model.takeSuccess
import core.sideeffects.dialogs.Dialogs
import core.sideeffects.dialogs.plugin.DialogConfig
import core.sideeffects.intents.Intents
import core.sideeffects.permissions.Permissions
import core.sideeffects.permissions.plugin.PermissionStatus
import core.sideeffects.resources.Resources
import core.sideeffects.toasts.Toasts
import core.views.LiveResult
import core.views.MutableLiveResult
import kotlinx.coroutines.launch

class CurrentColorViewModel(
    private val navigator: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val permissions: Permissions,
    private val intents: Intents,
    private val dialogs: Dialogs,
    private val colorsRepository: ColorsRepository
) : BaseViewModel() {

    private val _currentColor = MutableLiveResult<NamedColor>(PendingResult())
    val currentColor: LiveResult<NamedColor> = _currentColor

    private val colorListener: ColorListener = {
        _currentColor.postValue(SuccessResult(it)) // <-- hardcoding to get initial Success result every launch(with Error on 44 line -
                                                    // on the second time
    }

    // --- example of listening results via model layer

    init {
        colorsRepository.addListener(colorListener)
        load()
    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    // --- example of listening results directly from the screen

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is NamedColor) {
            val message = resources.getString(R.string.changed_color, result.name)
            toasts.toast(message)
        }
    }

    // ---

    fun changeColor() {
        val currentColor = currentColor.value.takeSuccess() ?: return
        val screen = ChangeColorFragment.Screen(currentColor.id)
        navigator.launch(screen)
    }

    fun tryAgain(){
        load()
    }

    /**
     * Example of using side-effect plugins
     */
    fun requestPermission() = viewModelScope.launch {
        val permission = permission.ACCESS_FINE_LOCATION
        val hasPermission = permissions.hasPermissions(permission)
        if (hasPermission) {
            dialogs.show(createPermissionAlreadyGrantedDialog())
        } else {
            when (permissions.requestPermission(permission)) {
                PermissionStatus.GRANTED -> {
                    toasts.toast(resources.getString(R.string.permissions_grated))
                }
                PermissionStatus.DENIED -> {
                    toasts.toast(resources.getString(R.string.permissions_denied))
                }
                PermissionStatus.DENIED_FOREVER -> {
                    if (dialogs.show(createAskForLaunchingAppSettingsDialog())) {
                        intents.openAppSettings()
                    }
                }
            }
        }
    }



    private fun createPermissionAlreadyGrantedDialog() = DialogConfig(
        title = resources.getString(R.string.dialog_permissions_title),
        message = resources.getString(R.string.permissions_already_granted),
        positiveButton = resources.getString(R.string.action_ok)
    )

    private fun createAskForLaunchingAppSettingsDialog() = DialogConfig(
        title = resources.getString(R.string.dialog_permissions_title),
        message = resources.getString(R.string.open_app_settings_message),
        positiveButton = resources.getString(R.string.action_open),
        negativeButton = resources.getString(R.string.action_cancel)
    )

    private fun load() = into(_currentColor){
        return@into colorsRepository.getCurrentColor()
    }

}

