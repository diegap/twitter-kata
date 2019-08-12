package katas.twitter.model.user

import katas.twitter.NonEmptyString

class User(val realName: RealName, val nickname: Nickname)

data class RealName(val value: String) : NonEmptyString(value)

data class Nickname(val value: String) : NonEmptyString(value)