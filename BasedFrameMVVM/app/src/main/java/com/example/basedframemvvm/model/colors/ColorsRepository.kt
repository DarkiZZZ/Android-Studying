package com.example.basedframemvvm.model.colors

import core.model.Repository
import kotlinx.coroutines.flow.Flow

typealias ColorListener = (NamedColor) -> Unit


interface ColorsRepository: Repository {

    suspend fun getCurrentColor(): NamedColor // Get current color/

    suspend fun setCurrentColor(color: NamedColor): Flow<Int> // Set current color as current/

    suspend fun getAvailableColors(): List<NamedColor> // Get list of all available color that may be chosen by user.

    suspend fun getById(id: Long) : NamedColor // Get the color content by its ID

    fun listenToCurrentColor(): Flow<NamedColor>
}