package katas.twitter.actions

import katas.twitter.model.user.User
import katas.twitter.repositories.UserRepository

class UpdateUser(private val userRepository: UserRepository) {
    fun execute(user: User) {
        userRepository.find(user.nickname).let {
            when {
                it.isDefined() -> userRepository.save(it.orNull()!!)
                else -> throw RuntimeException("User ${user.nickname} does not exist")
            }
        }
    }
}