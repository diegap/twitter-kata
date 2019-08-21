package katas.twitter.infra

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import katas.twitter.domain.actions.*
import katas.twitter.domain.repositories.TweetRepository
import katas.twitter.infra.repositories.MongoUserRepository
import katas.twitter.domain.repositories.UserRepository
import katas.twitter.infra.config.MongoConfig
import katas.twitter.infra.repositories.MongoTweetRepository
import org.koin.dsl.module

val twitterModule = module {
    val config = ConfigFactory.load()
    val mongoConfig = config.extract<MongoConfig>("mongodb")

    single {
        MongoUserRepository(mongoConfig) as UserRepository
    }

    single {
        MongoTweetRepository(mongoConfig) as TweetRepository
    }

    single {
        RegisterUser(get())
    }

    single {
        UpdateUser(get())
    }

    single {
        GetUser(get())
    }

    single {
        FollowUser(get())
    }

    single {
        AskFollows(get())
    }

    single {
        SendTweet(get())
    }

    single {
        ReadTweets(get())
    }
}