package katas.twitter.actions

import katas.twitter.model.user.Nickname
import katas.twitter.repositories.UserRepository

class FollowUser(private val userRepository: UserRepository) {
    fun execute(user: Nickname, follow: Nickname) {
       userRepository.find(user).map {
           it.copy(follows = it.follows.plusElement(follow)).let { userToUpdate ->
               userRepository.save(userToUpdate)
           }
       }
    }
}