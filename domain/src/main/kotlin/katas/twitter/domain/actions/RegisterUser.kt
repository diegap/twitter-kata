package katas.twitter.domain.actions

import katas.twitter.domain.model.user.User
import katas.twitter.domain.repositories.UserRepository

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