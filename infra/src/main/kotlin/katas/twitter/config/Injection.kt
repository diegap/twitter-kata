package katas.twitter.config

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import katas.twitter.actions.AskFollows
import katas.twitter.actions.FollowUser
import katas.twitter.actions.RegisterUser
import katas.twitter.actions.UpdateUser
import katas.twitter.repositories.MongoUserRepository
import katas.twitter.repositories.UserRepository
import org.koin.dsl.module

val twitterModule = module {
    val config = ConfigFactory.load()
    val mongoConfig = config.extract<MongoConfig>("mongodb")

    single {
        MongoUserRepository(mongoConfig) as UserRepository
    }

    single {
        RegisterUser(get())
    }

    single {
        UpdateUser(get())
    }

    single {
        FollowUser(get())
    }

    single {
        AskFollows(get())
    }
}