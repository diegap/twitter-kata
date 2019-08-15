package katas.twitter.domain.actions

import katas.twitter.domain.model.tweet.Tweet
import katas.twitter.domain.model.user.Nickname
import katas.twitter.domain.repositories.TweetRepository

class ReadTweets(private val tweetRepository: TweetRepository) {
    fun execute(nickname: Nickname): Set<Tweet> = tweetRepository.find(nickname)
}
