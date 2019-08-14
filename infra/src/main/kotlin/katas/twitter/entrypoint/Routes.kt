package katas.twitter.entrypoint

import arrow.core.Try
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import katas.twitter.actions.RegisterUser
import katas.twitter.koinProxy
import katas.twitter.model.user.Nickname
import katas.twitter.model.user.RealName
import katas.twitter.model.user.User
import mu.KotlinLogging
import org.apache.http.HttpStatus

internal fun pingRoute(parentRoute: Route): Route {
    val logger = KotlinLogging.logger {}

    parentRoute {
        get("/") {
            call.respond(HttpStatusCode.OK, Message("Hello Twitter!"))
        }
        post("/users"){
            call.receive<RestUser>().toDomain().let {
                logger.debug { "Registering user $it" }
                val registrationResult = Try { koinProxy.get<RegisterUser>().execute(it)}
                registrationResult.fold({
                    logger.error { it }
                    call.respond(HttpStatusCode.InternalServerError, "Cannot register user")
                }, {
                    call.respond(HttpStatusCode.Created, "User registered successfully")
                })
            }
        }
    }
    return parentRoute
}

internal data class Message(val value: String)

internal data class RestUser(val realName: String?, val nickname: String?, val follows: List<String>?){
    fun toDomain(): User = User(
            realName = RealName(realName!!),
        nickname = Nickname(nickname!!),
        follows = follows?.map { Nickname(it) }?.toSet() ?: emptySet()
    )
}