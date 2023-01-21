package ru.msokolov.numbertraining.domain.usecases

import ru.msokolov.numbertraining.domain.entities.GameSettings
import ru.msokolov.numbertraining.domain.entities.Level
import ru.msokolov.numbertraining.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings{
        return repository.getGameSettings(level = level)
    }
}