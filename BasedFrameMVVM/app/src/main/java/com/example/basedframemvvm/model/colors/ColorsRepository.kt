package com.example.basedframemvvm.model.colors

import core.model.Repository

typealias ColorListener = (NamedColor) -> Unit


interface ColorsRepository: Repository {

    fun getCurrentColor(): Task<NamedColor> // Get current color/

    fun setCurrentColor(color: NamedColor): Task<Unit> // Set current color as current/

    fun getAvailableColors(): List<NamedColor> // Get list of all available color that may be chosen by user.

    fun getById(id: Long) : Task<NamedColor> // Get the color content by its ID

    fun addListener(listener: ColorListener) // Listener is triggered immediately with current value when calling this method

    fun removeListener(listener: ColorListener)
}