package katas.twitter.actions

import arrow.core.Option
import katas.twitter.model.user.FollowedUser
import katas.twitter.model.user.Nickname
import katas.twitter.repositories.FollowedUserRepository

class AskFollows(private val followedUserRepository: FollowedUserRepository) {
    fun execute(nickname: Nickname) : Option<FollowedUser> = followedUserRepository.find(nickname)
}