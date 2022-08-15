package core.views

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates(){
        (requireActivity() as FragmentsHolder).notifyScreenUpdates()
    }
}
