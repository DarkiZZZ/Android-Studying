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

class ActivityScopeViewModel(
    val uiActions: UiActions,
    val navigator: IntermediateNavigator
): ViewModel(), Navigator by navigator, UiActions by uiActions{

    val whenActivityActive = ResourceActions<MainActivity>()



    override fun onCleared() {
        super.onCleared()
        navigator.clear()
    }

}
