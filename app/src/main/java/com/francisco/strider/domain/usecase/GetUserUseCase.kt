package com.francisco.strider.domain.usecase

import com.francisco.strider.commons.extensions.safeRunDispatcher
import com.francisco.strider.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun execute(id: Long) = safeRunDispatcher {
        repository.getUser(id = id)
    }
}