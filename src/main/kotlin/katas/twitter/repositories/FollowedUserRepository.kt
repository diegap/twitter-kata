package katas.twitter.repositories

import arrow.core.Option
import katas.twitter.model.user.FollowedUser
import katas.twitter.model.user.Nickname

interface FollowedUserRepository {
    fun find(followedUser: Nickname) : Option<FollowedUser>
    fun save(followedUser: FollowedUser)
}
