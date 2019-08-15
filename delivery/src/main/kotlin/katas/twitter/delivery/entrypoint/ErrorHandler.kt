package katas.twitter.delivery.entrypoint

import arrow.core.Try
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import mu.KotlinLogging

internal fun StatusPages.Configuration.registerExceptionHandling() {
    val logger = KotlinLogging.logger {}
    exception<Exception> {
        Try {
            jacksonObjectMapper().writeValueAsString(Message(it.message!!))
        }.fold({
            logger.error("Couldn't map error message", it)
        }, {
            call.respond(HttpStatusCode.InternalServerError, it)
        })
    }
}