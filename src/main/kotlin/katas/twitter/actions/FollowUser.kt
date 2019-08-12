package katas.twitter.actions

import katas.twitter.model.user.Nickname
import katas.twitter.repositories.UserRepository

class FollowUser(private val userRepository: UserRepository) {
    fun execute(user: Nickname, follower: Nickname) {
       userRepository.find(user).map {
           val follow = it.copy(follows = it.follows.plusElement(follower))
           userRepository.save(follow)
       }
    }
}