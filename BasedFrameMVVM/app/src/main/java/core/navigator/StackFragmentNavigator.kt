package core.navigator

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import core.ARG_SCREEN
import core.utils.Event
import core.views.BaseFragment
import core.views.BaseScreen
import core.views.HasScreenTitle

class StackFragmentNavigator(
    private val activity: AppCompatActivity,
    @IdRes private val containerId: Int,
    private val defaultTitle: String,
    private val animations: Animations,
    private val initialScreenCreator: () -> BaseScreen
): Navigator {

    override fun launch(screen: BaseScreen){
        launchFragment(screen)
    }

    override fun goBack(result: Any?) {
        if (result != null) {
            this.result = Event(result)
        }
        activity.onBackPressed()
    }


    private var result : Event<Any>? = null

    fun onCreate(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            launchFragment(
                screen = initialScreenCreator(),
                addToBackStack = false
            )
        }
        activity.supportFragmentManager.
            registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    fun onDestroy(){
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    fun launchFragment(screen: BaseScreen, addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
        .setCustomAnimations(
        animations.animEnter,
        animations.animExit,
        animations.animPopEnter,
        animations.animPopExit
        )
        .replace(containerId,fragment)
            .commit()
    }

    fun notifyScreenUpdates(){
        val fragment = activity.supportFragmentManager.findFragmentById(containerId)

        if (activity.supportFragmentManager.backStackEntryCount > 0){
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        else{
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        if (fragment is HasScreenTitle && fragment.getScreenTitle() != null){
            activity.supportActionBar?.title = fragment.getScreenTitle()
        }
        else{
            activity.supportActionBar?.title = defaultTitle
        }
    }

    private fun publishResults(fragment: Fragment){
        val result = result?.getValue() ?: return
        if (fragment is BaseFragment) {
            fragment.viewModel.onResult(result)
        }
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentCreated(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            savedInstanceState: Bundle?
        ) {
            notifyScreenUpdates()
            publishResults(fragment)
        }
    }

    class Animations(
        @AnimRes val animEnter: Int,
        @AnimRes val animExit: Int,
        @AnimRes val animPopEnter: Int,
        @AnimRes val animPopExit: Int
    )
}
