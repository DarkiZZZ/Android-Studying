package core

import android.app.Application
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.basedframemvvm.App
import com.example.basedframemvvm.MainActivity
import com.example.basedframemvvm.R
import core.navigator.IntermediateNavigator
import core.utils.Event
import core.utils.ResourceActions
import core.navigator.Navigator
import core.uiactions.UiActions
import core.views.BaseScreen
import core.views.LiveEvent
import core.views.MutableLiveEvent

const val ARG_SCREEN = "ARG_SCREEN"

class MainViewModel(
    val uiActions: UiActions,
    val navigator: IntermediateNavigator
): ViewModel(), Navigator by navigator, UiActions by uiActions{

    val whenActivityActive = ResourceActions<MainActivity>()

    private val _result = MutableLiveEvent<Any>()
    val result: LiveEvent<Any> = _result



   /* override fun launch(screen: BaseScreen) = whenActivityActive{
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
            *//*.setCustomAnimations(
                //todo,
                //todo,
                //todo,
                //todo
            )*//*
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }*/

    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }

    /*companion object{
        private const val ARG_SCREEN = "ARG_SCREEN"
    }*/


}
