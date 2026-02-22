package br.com.fiap.eco.repository

import android.content.Context
import br.com.fiap.eco.dao.EcoDatabase
import br.com.fiap.eco.model.User

class RoomUserRepository(context: Context) : UserRepository {
    private val userDao = EcoDatabase
        .getDatabase(context).userDao()

    override fun saveUser(user: User) {
        userDao.save(user)
    }

    override fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    override fun login(email: String, password: String): Boolean {
        val user = userDao.login(email, password)
        return user != null
    }

    override fun updateUser(user: User): Int {
        return userDao.update(user)
    }

    override fun deleteUser(user: User): Int {
        return userDao.delete(user)
    }
}
