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
import core.model.tasks.dispatchers.Dispatcher
import core.model.tasks.factories.TasksFactory
import core.sideeffects.dialogs.Dialogs
import core.sideeffects.dialogs.plugin.DialogConfig
import core.views.LiveResult
import core.views.MutableLiveResult

class CurrentColorViewModel(
    private val navigator: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val permissions: Permissions,
    private val intents: Intents,
    private val dialogs: Dialogs,
    private val colorsRepository: ColorsRepository,
    private val tasksFactory: TasksFactory,
    dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {

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
            val message = uiActions.getString(R.string.changed_color, result.name)
            uiActions.toast(message)
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
    
    fun requestPermission() = tasksFactory.async<Unit>{
        val permission = permission.ACCESS_FINE_LOCATION
        val hasPermission = permissions.hasPermissions(permission)
        if (hasPermission){
            dialogs.show(createPermissionAlreadyGrantedDialog()).await()
        }
        else{
            when(permissions.requestPermission(permission).await()){
                
                PermissionStatus.GRANTED -> {
                    toasts.toast(uiActions.getString(R.string.permissions_granted_message))
                }
                PermissionStatus.DENIED -> {
                    toasts.toast(uiActions.getString(R.string.permissions_denied_message))
                }
                PermissionStatus.DENIED_FOREVER -> {
                    if (dialogs.show(createAskForLaunchingAppSettingsDialog()).await()){
                        intents.openAppSettings()
                    }
                }
            }
        }
    }.safeEnqueue()
    
    private fun createPermissionAlreadyGrantedDialog() = DialogConfig(
        title = uiActions.getString(R.string.title_permission),
        message = uiActions.getString(R.string.message_permission),
        positiveButton = uiActions.getString(R.string.positive_button)
    )

    private fun createAskForLaunchingAppSettingsDialog() = DialogConfig(
        
    )

    private fun load(){
        colorsRepository.getCurrentColor().into(_currentColor)
    }

}

