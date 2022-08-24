package com.francisco.strider.domain.usecase

import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.SafeRunException
import com.francisco.strider.commons.extensions.checkDateEqualsToday
import com.francisco.strider.commons.extensions.formatDateAsDayMonthYear
import com.francisco.strider.commons.extensions.safeRunDispatcher
import com.francisco.strider.domain.repository.PostRepository
import javax.inject.Inject

class SavePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend fun execute(post: String) = safeRunDispatcher {
        if (todayPostQuantity() > 4) {
            throw SafeRunException(Error(message = "Post limit exceeded"))
        } else {
            repository.savePosts(post = post)
        }
    }

    private suspend fun todayPostQuantity(): Int {
        return repository.getPosts().filter {
            checkDateEqualsToday(it.creation.formatDateAsDayMonthYear())
        }.size
    }

}