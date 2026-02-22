package br.com.fiap.eco.repository

import br.com.fiap.eco.model.User

interface UserRepository {
    fun saveUser(user: User)
    fun getUserByEmail(email: String): User?
    fun login(email: String, password: String): Boolean
    fun updateUser(user: User): Int
    fun deleteUser(user: User): Int
}
