package katas.twitter.domain.repositories

import arrow.core.Option
import katas.twitter.domain.model.user.Nickname
import katas.twitter.domain.model.user.User

interface UserRepository {
    fun find(nickname: Nickname) : Option<User>
    fun save(user: User)
}
