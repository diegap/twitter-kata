package katas.twitter.delivery.entrypoint

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import katas.twitter.domain.actions.AskFollows
import katas.twitter.domain.actions.FollowUser
import katas.twitter.domain.actions.RegisterUser
import katas.twitter.domain.actions.UpdateUser
import katas.twitter.delivery.koinProxy
import katas.twitter.domain.model.user.Nickname
import katas.twitter.domain.model.user.RealName
import katas.twitter.domain.model.user.User
import mu.KotlinLogging

val logger = KotlinLogging.logger {}

internal fun pingRoute(parentRoute: Route): Route {
    parentRoute {
        get("/") {
            logger.debug { "ping!" }
            call.respond(HttpStatusCode.OK, Message("Hello Twitter!"))
        }
    }
    return parentRoute
}

internal fun userRoutes(parentRoute: Route) : Route {
    parentRoute {
        post("/users") {
            val user = call.receive<NewUser>()
            logger.debug { "Registering user $user" }
            koinProxy.get<RegisterUser>().execute(user.toDomain())
            call.respond(HttpStatusCode.Created, "User $user registered successfully")
        }
        put("/users/{nickname}") {
            val user = call.receive<NewUser>()
            logger.debug { "Updating user >> ${user.nickname}" }
            koinProxy.get<UpdateUser>().execute(user.toDomain())
            call.respond(HttpStatusCode.OK, "User ${user.nickname} updated")
        }
        post("/users/{nickname}/follows"){
            val follow = call.receive<UserFollow>()
            val user = call.parameters["nickname"]
            logger.debug { "User $user will follow ${follow.nickname}" }
            koinProxy.get<FollowUser>().execute(Nickname(user!!), Nickname(follow.nickname!!))
            call.respond(HttpStatusCode.OK, "User ${follow.nickname} is followed by $user")
        }
        get("/users/{nickname}"){
            val user = call.parameters["nickname"]
            logger.debug { "Retrieving list of follows for user $user" }
            val follows = koinProxy.get<AskFollows>().execute(Nickname(user!!))
            call.respond(HttpStatusCode.OK, follows)
        }
    }
    return parentRoute
}

internal data class Message(val value: String)

internal data class NewUser(val realName: String?, val nickname: String?, val follows: List<String>?) {
    fun toDomain(): User = User(
            realName = RealName(realName!!),
            nickname = Nickname(nickname!!),
            follows = follows?.map { Nickname(it) }?.toSet() ?: emptySet()
    )
}
internal data class UserFollow(val nickname: String?)
