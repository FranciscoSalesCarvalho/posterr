package com.francisco.strider.domain.usecase

import com.francisco.strider.commons.extensions.safeRunDispatcher
import com.francisco.strider.domain.repository.EventsRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: EventsRepository
) {

    suspend fun execute() = safeRunDispatcher {
        repository.getPosts()
    }
}