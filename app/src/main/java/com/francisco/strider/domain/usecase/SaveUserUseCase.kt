package com.francisco.strider.domain.usecase

import com.francisco.strider.commons.extensions.safeRunDispatcher
import com.francisco.strider.data.entities.User
import com.francisco.strider.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(user: User) = safeRunDispatcher {
        repository.saveUser(user = user)
    }
}