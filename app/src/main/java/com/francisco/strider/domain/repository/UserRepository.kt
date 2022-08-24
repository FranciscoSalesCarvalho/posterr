package com.francisco.strider.domain.repository

import com.francisco.strider.data.entities.User

interface UserRepository {

    suspend fun getUser(id: Long): User
    suspend fun saveUser(user: User)
}