package com.example.basedframemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basedframemvvm.views.currentcolor.CurrentColorFragment
import core.ActivityScopeViewModel
import core.navigator.IntermediateNavigator
import core.navigator.StackFragmentNavigator
import core.uiactions.AndroidUiActions
import core.utils.viewModelCreator
import core.views.FragmentsHolder

class MainActivity : AppCompatActivity(), FragmentsHolder {

    private lateinit var navigator: StackFragmentNavigator

    private val activityViewModel by viewModelCreator<ActivityScopeViewModel>{
        ActivityScopeViewModel(
            uiActions = AndroidUiActions(applicationContext),
            navigator = IntermediateNavigator()
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator = StackFragmentNavigator(
            activity = this,
            containerId = R.id.fragmentContainer,
            defaultTitle = getString(R.string.app_name),
            animations = StackFragmentNavigator.Animations(
                animEnter = R.anim.enter,
                animExit = R.anim.exit,
                animPopEnter = R.anim.pop_enter,
                animPopExit = R.anim.pop_exit
            ),
            initialScreenCreator = {CurrentColorFragment.Screen()}
        )
        navigator.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        navigator.onDestroy()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.navigator.setTarget(navigator)
    }

    override fun onPause() {
        super.onPause()
        activityViewModel.navigator.setTarget(null)
    }

    override fun notifyScreenUpdates() {
        navigator.notifyScreenUpdates()
    }

    override fun getActivityScopeViewModel(): ActivityScopeViewModel {
        return activityViewModel
    }


}