package katas.twitter.actions

import katas.twitter.model.user.User
import katas.twitter.repositories.UserRepository

class RegisterUser(private val userRepository: UserRepository) {
    fun execute(user: User) {
        userRepository.find(user.nickname).let {
            when {
                it.isDefined() -> throw RuntimeException("User ${user.nickname.value} already registered")
                else -> userRepository.save(user)
            }
        }
    }
}