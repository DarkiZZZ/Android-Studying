package ru.msokolov.numbertraining.domain.repository

import ru.msokolov.numbertraining.domain.entities.GameSettings
import ru.msokolov.numbertraining.domain.entities.Level
import ru.msokolov.numbertraining.domain.entities.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}