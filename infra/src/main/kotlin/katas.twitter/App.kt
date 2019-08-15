package katas.twitter

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import katas.twitter.actions.AskFollowers
import katas.twitter.actions.FollowUser
import katas.twitter.actions.RegisterUser
import katas.twitter.actions.UpdateUser
import katas.twitter.config.MongoConfig
import katas.twitter.entrypoint.pingRoute
import katas.twitter.entrypoint.registerExceptionHandling
import katas.twitter.entrypoint.userRoutes
import katas.twitter.repositories.MongoUserRepository
import katas.twitter.repositories.UserRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun Application.ktorMain() {
    installFeatures()
}

private fun Application.installFeatures() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Compression)
    install(StatusPages) {
        registerExceptionHandling()
    }
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter("  ", "\n"))
            })
        }
    }
    install(Routing) {
        route("/api/v1") {
            pingRoute(this)
            userRoutes(this)
        }
    }
}

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
        AskFollowers(get())
    }
}

fun main() {
    startKoin {
        modules(twitterModule)
        embeddedServer(Netty, commandLineEnvironment(arrayOf())).start()
    }
}