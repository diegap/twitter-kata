package katas.twitter.domain.repositories

import katas.twitter.domain.model.tweet.Tweet
import katas.twitter.domain.model.user.Nickname

interface TweetRepository {
    fun save(tweet: Tweet)
    fun find(nickname: Nickname) : Set<Tweet>
}
