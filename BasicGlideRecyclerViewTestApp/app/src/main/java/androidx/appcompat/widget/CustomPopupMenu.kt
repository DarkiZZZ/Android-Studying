package androidx.appcompat.widget

import android.content.Context
import android.view.View

class CustomPopupMenu(context: Context, view: View) : PopupMenu(context, view) {

    init {
        setForceShowIcon(true)
    }
}