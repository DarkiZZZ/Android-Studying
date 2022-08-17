package core.views

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import core.model.ErrorResult
import core.model.PendingResult
import core.model.Result
import core.model.SuccessResult

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates(){
        (requireActivity() as FragmentsHolder).notifyScreenUpdates()
    }

    fun <T> renderResult(root: View, result: Result<T>,
                         onPending: () -> Unit,
                         onError: (Exception) -> Unit,
                         onSuccess: (T) -> Unit){
        (root as ViewGroup).children.forEach { it.visibility = View.GONE }
        when(result){
            is SuccessResult -> onSuccess(result.data as T)
            is ErrorResult -> onError(result.exception)
            is PendingResult -> onPending()
        }
    }
}
