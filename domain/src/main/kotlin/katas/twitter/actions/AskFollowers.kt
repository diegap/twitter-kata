package katas.twitter.actions

import arrow.core.getOrElse
import katas.twitter.model.user.Nickname
import katas.twitter.repositories.UserRepository

class AskFollowers(private val userRepository: UserRepository) {
    fun execute(nickname: Nickname) =
            userRepository.find(nickname).map { it.follows }.getOrElse { emptySet() }
}
