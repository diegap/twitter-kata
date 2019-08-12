package katas.twitter.actions

import katas.twitter.model.user.Nickname
import katas.twitter.repositories.FollowedUserRepository

class FollowUser(private val followedUserRepository: FollowedUserRepository) {
    fun execute(user: Nickname, follower: Nickname) {
       followedUserRepository.find(user).map {
           val follow = it.copy(followers = it.followers.plusElement(follower))
           followedUserRepository.save(follow)
       }
    }
}