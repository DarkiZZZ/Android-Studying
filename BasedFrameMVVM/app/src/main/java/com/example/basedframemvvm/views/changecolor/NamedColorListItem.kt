package com.example.basedframemvvm.views.changecolor

import com.example.basedframemvvm.model.colors.NamedColor

data class NamedColorListItem(
    val namedColor: NamedColor,
    val selected: Boolean
)