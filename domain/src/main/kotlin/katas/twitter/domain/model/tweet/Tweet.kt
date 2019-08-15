package katas.twitter.domain.model.tweet

import katas.twitter.domain.NonEmptyString
import katas.twitter.domain.model.user.Nickname

data class Tweet(val nickName: Nickname, val content: TweetContent)

data class TweetContent(val value: String) : NonEmptyString(value)
