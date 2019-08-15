package katas.twitter.feature

import com.nhaarman.mockitokotlin2.*
import katas.twitter.domain.actions.ReadTweets
import katas.twitter.domain.actions.SendTweet
import katas.twitter.domain.model.tweet.Tweet
import katas.twitter.domain.model.tweet.TweetContent
import katas.twitter.domain.model.user.Nickname
import katas.twitter.domain.repositories.TweetRepository
import org.amshove.kluent.`should equal`
import org.mockito.Mockito.doNothing
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object TweetFeature : Spek({

    val sender = Nickname("@joey")
    val tweet = Tweet(nickName=sender, content= TweetContent("HEY HO!"))

    Feature("Send tweet"){
        Scenario("Twitting message"){
            val tweetRepository = mock<TweetRepository> {
                doNothing().`when`(it).save(tweet)
            }
            When("a tweet is sent by user"){
                SendTweet(tweetRepository).execute(tweet)
            }
            Then("tweet is saved"){
                argumentCaptor<Tweet>{
                    verify(tweetRepository, atMost(1)).save(capture())
                    firstValue.content.value `should equal` "HEY HO!"
                }
            }
        }
    }

    Feature("Read tweets"){
        Scenario("reading tweets of a given user"){
            var tweets = emptySet<Tweet>()
            val tweetRepository = mock<TweetRepository> {
                on { find(sender) } doReturn setOf(tweet)
            }
            When("a user wants to read tweets from a follow"){
                tweets = ReadTweets(tweetRepository).execute(sender)
            }
            Then("a list of tweets is retrieved"){
                tweets.size `should equal` 1
            }
        }
    }
})