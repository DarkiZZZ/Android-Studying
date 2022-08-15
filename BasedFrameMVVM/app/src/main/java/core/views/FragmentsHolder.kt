package core.views

import core.ActivityScopeViewModel

interface FragmentsHolder {

    fun notifyScreenUpdates()

    fun getActivityScopeViewModel(): ActivityScopeViewModel
}