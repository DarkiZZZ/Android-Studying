package core.sideeffects.navigator.plugin

import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import core.ARG_SCREEN
import core.sideeffects.SideEffectImplementation
import core.sideeffects.navigator.Navigator
import core.utils.Event
import core.views.BaseFragment
import core.views.BaseScreen
import core.views.HasScreenTitle

class StackFragmentNavigator(
    @IdRes private val containerId: Int,
    private val defaultTitle: String,
    private val animations: Animations,
    private val initialScreenCreator: () -> BaseScreen
): SideEffectImplementation(), Navigator, LifecycleObserver {

    override fun launch(screen: BaseScreen){
        launchFragment(screen)
    }

    override fun goBack(result: Any?) {
        if (result != null) {
            this.result = Event(result)
        }
        requireActivity().onBackPressed()
    }


    private var result : Event<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?){
        requireActivity().lifecycle.addObserver(this)
        if (savedInstanceState == null){
            launchFragment(
                screen = initialScreenCreator(),
                addToBackStack = false
            )
        }
        requireActivity().supportFragmentManager.
            registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        requireActivity().supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    override fun onBackPressed(): Boolean{
        val fragment = getCurrentFragment()
        return if (fragment is BaseFragment){
            fragment.viewModel.onBackPressed()
        } else{
            false
        }
    }

    fun launchFragment(screen: BaseScreen, addToBackStack: Boolean = true) {
        val fragment = screen.javaClass.enclosingClass.newInstance() as Fragment
        fragment.arguments = bundleOf(ARG_SCREEN to screen)

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
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

    override fun onSupportNavigateUp(): Boolean {
        requireActivity().onBackPressed()
        return true
    }

    override fun onRequestUpdates(){
        val fragment = getCurrentFragment()

        if (requireActivity().supportFragmentManager.backStackEntryCount > 0){
            requireActivity().supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        else{
            requireActivity().supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        if (fragment is HasScreenTitle && fragment.getScreenTitle() != null){
            requireActivity().supportActionBar?.title = fragment.getScreenTitle()
        }
        else{
            requireActivity().supportActionBar?.title = defaultTitle
        }
    }

    private fun getCurrentFragment(): Fragment?{
        return requireActivity().supportFragmentManager.findFragmentById(containerId)
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
            onRequestUpdates()
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
