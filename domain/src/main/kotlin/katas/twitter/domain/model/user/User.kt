package katas.twitter.domain.model.user

import katas.twitter.domain.NonEmptyString

data class User(val realName: RealName, val nickname: Nickname, val follows: Set<Nickname>) {
    fun isFollowing(nickname: Nickname) = follows.contains(nickname)
}

data class RealName(val value: String) : NonEmptyString(value)

data class Nickname(val value: String) : NonEmptyString(value)