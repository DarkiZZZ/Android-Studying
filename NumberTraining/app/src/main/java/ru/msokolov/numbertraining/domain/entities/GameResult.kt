package ru.msokolov.numbertraining.domain.entities

data class GameResult(
    val isWon: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
)