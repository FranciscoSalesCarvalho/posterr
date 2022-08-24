package com.francisco.strider.domain.usecase

import com.francisco.strider.commons.extensions.safeRunDispatcher
import com.francisco.strider.domain.repository.PostRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend fun execute() = safeRunDispatcher {
        repository.getPosts().reversed()
    }
}