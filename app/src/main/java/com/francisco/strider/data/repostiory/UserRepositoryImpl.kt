package com.francisco.strider.data.repostiory

import com.francisco.strider.data.database.dao.UserDao
import com.francisco.strider.data.entities.User
import com.francisco.strider.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {

    override suspend fun getUser(id: Long): User {
        return dao.getUser(id = id)
    }

    override suspend fun saveUser(user: User) {
        return dao.saveUser(user = user)
    }
}