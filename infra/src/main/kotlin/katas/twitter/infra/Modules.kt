package katas.twitter.infra

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import katas.twitter.domain.actions.AskFollows
import katas.twitter.domain.actions.FollowUser
import katas.twitter.domain.actions.RegisterUser
import katas.twitter.domain.actions.UpdateUser
import katas.twitter.infra.repositories.MongoUserRepository
import katas.twitter.domain.repositories.UserRepository
import katas.twitter.infra.config.MongoConfig
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