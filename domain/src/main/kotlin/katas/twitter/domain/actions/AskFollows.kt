package katas.twitter.domain.actions

import arrow.core.getOrElse
import katas.twitter.domain.model.user.Nickname
import katas.twitter.domain.repositories.UserRepository

class AskFollows(private val userRepository: UserRepository) {
    fun execute(nickname: Nickname) =
            userRepository.find(nickname).map { it.follows }.getOrElse { emptySet() }
}
