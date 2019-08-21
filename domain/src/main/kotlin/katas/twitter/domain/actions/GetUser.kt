package katas.twitter.domain.actions

import katas.twitter.domain.model.user.Nickname
import katas.twitter.domain.repositories.UserRepository

class GetUser(private val userRepository: UserRepository) {
    fun execute(user: Nickname) = userRepository.find(user)
}