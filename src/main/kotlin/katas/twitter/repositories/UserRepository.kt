package katas.twitter.repositories

import arrow.core.Option
import katas.twitter.model.user.Nickname
import katas.twitter.model.user.User

interface UserRepository {
    fun find(nickname: Nickname) : Option<User>
    fun save(user: User)
}
