package katas.twitter.domain.actions

import katas.twitter.domain.model.user.Nickname
import katas.twitter.domain.repositories.UserRepository

class FollowUser(private val userRepository: UserRepository) {
    fun execute(user: Nickname, follow: Nickname) {
       userRepository.find(user).map {
           it.copy(follows = it.follows.plusElement(follow)).let { userToUpdate ->
               userRepository.save(userToUpdate)
           }
       }
    }
}