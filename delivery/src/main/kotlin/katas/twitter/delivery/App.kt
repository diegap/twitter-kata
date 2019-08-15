package katas.twitter.delivery

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import katas.twitter.infra.twitterModule
import katas.twitter.delivery.entrypoint.pingRoute
import katas.twitter.delivery.entrypoint.registerExceptionHandling
import katas.twitter.delivery.entrypoint.userRoutes
import org.koin.core.context.startKoin

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

fun main() {
    startKoin {
        modules(twitterModule)
        embeddedServer(Netty, commandLineEnvironment(arrayOf())).start()
    }
}