package core.model

sealed class Progress

object EmptyProgress : Progress()

data class PercentageProgress(
    val percentage: Int
) : Progress(){

    companion object{
        val START = PercentageProgress(0)
    }
}

// extension methods:

fun Progress.isInProgress() = this !is EmptyProgress

fun Progress.getPercentage() = (this as? PercentageProgress)?.percentage ?: PercentageProgress.START.percentage