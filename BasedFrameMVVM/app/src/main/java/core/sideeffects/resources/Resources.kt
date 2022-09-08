package core.sideeffects.resources

import androidx.annotation.StringRes

interface Resources {

    fun getString(@StringRes resId: Int, vararg args: Any): String
}