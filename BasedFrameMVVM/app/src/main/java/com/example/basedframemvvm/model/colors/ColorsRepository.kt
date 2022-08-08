package com.example.basedframemvvm.model.colors

import com.example.basedframemvvm.model.Repository

typealias ColorListener = (NamedColor) -> Unit


interface ColorsRepository: Repository {

    var currentColor: NamedColor

    fun getAvailableColors(): List<NamedColor>

    fun getById(id: Long) : NamedColor

    fun addListener(listener: ColorListener)

    fun removeListener(listener: ColorListener)
}