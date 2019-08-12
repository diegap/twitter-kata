package katas.twitter.actions

import katas.twitter.model.user.User
import katas.twitter.repositories.UserRepository

class RegisterUser(private val userRepository: UserRepository) {

    fun execute(user: User) {
        val find = userRepository.find(user.nickname)
        when {
            find.isDefined() -> throw RuntimeException("User ${user.nickname} already registered")
            else -> userRepository.save(user)
        }
    }
}