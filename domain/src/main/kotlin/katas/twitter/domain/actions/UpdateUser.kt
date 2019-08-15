package katas.twitter.domain.actions

import katas.twitter.domain.model.user.User
import katas.twitter.domain.repositories.UserRepository

class UpdateUser(private val userRepository: UserRepository) {
    fun execute(user: User) {
        userRepository.find(user.nickname).let {
            when {
                it.isDefined() -> userRepository.save(user)
                else -> throw RuntimeException("User ${user.nickname.value} does not exist")
            }
        }
    }
}