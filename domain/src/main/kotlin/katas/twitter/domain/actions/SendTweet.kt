package katas.twitter.domain.actions

import katas.twitter.domain.model.tweet.Tweet
import katas.twitter.domain.repositories.TweetRepository

class SendTweet(private val tweetRepository: TweetRepository) {
    fun execute(tweet: Tweet) {
        tweetRepository.save(tweet)
    }
}
