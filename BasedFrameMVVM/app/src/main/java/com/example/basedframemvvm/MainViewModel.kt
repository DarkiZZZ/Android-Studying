package com.example.basedframemvvm

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import com.example.basedframemvvm.utils.Event
import com.example.basedframemvvm.utils.ResourceActions
import com.example.basedframemvvm.views.Navigator
import com.example.basedframemvvm.views.UiActions
import com.example.basedframemvvm.views.base.BaseScreen
import com.example.basedframemvvm.views.base.LiveEvent
import com.example.basedframemvvm.views.base.MutableLiveEvent

const val ARG_SCREEN = "ARG_SCREEN"

class MainViewModel(
    application: Application): AndroidViewModel(
    application), Navigator, UiActions {

    val whenActivityActive = ResourceActions<MainActivity>()

    private val _result = MutableLiveEvent<Any>()
    val result: LiveEvent<Any> = _result

    override fun toast(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messageRes: Int, vararg args: Any): String {
        return getApplication<App>().getString(messageRes, *args)
    }

    override fun launch(screen: BaseScreen) = whenActivityActive{
        launchFragment(it, screen)
    }

    override fun goBack(result: Any?) = whenActivityActive {
        if (result != null) {
            _result.value = Event(result)
        }
        it.onBackPressed()
    }

    fun launchFragment(activity: MainActivity, screen: BaseScreen, addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            /*.setCustomAnimations(
                //todo,
                //todo,
                //todo,
                //todo
            )*/
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }

    override fun onCleared() {
        super.onCleared()
        whenActivityActive.clear()
    }

    /*companion object{
        private const val ARG_SCREEN = "ARG_SCREEN"
    }*/


}
