package katas.twitter.delivery.entrypoint

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import katas.twitter.delivery.koinProxy
import katas.twitter.domain.actions.*
import katas.twitter.domain.model.tweet.Tweet
import katas.twitter.domain.model.tweet.TweetContent
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

internal fun userRoutes(parentRoute: Route): Route {
    parentRoute {
        post("/users") {
            val user = call.receive<RestUser>()
            logger.debug { "Registering user $user" }
            koinProxy.get<RegisterUser>().execute(user.toDomain())
            call.respond(HttpStatusCode.Created, "User $user registered successfully")
        }
        put("/users/{nickname}") {
            val user = call.receive<RestUser>()
            logger.debug { "Updating user >> ${user.nickname}" }
            koinProxy.get<UpdateUser>().execute(user.toDomain())
            call.respond(HttpStatusCode.OK, "User ${user.nickname} updated")
        }
        post("/users/{nickname}/follows") {
            val follow = call.receive<UserFollow>()
            val user = call.parameters["nickname"].orEmpty()
            logger.debug { "User $user will follow ${follow.nickname}" }
            koinProxy.get<FollowUser>().execute(Nickname(user), Nickname(follow.nickname))
            call.respond(HttpStatusCode.OK, "User ${follow.nickname} is followed by $user")
        }
        get("/users/{nickname}") {
            val nickname = call.parameters["nickname"].orEmpty()
            logger.debug { "Retrieving user $nickname" }
            val user = koinProxy.get<GetUser>().execute(Nickname(nickname))
                    .map { user ->
                        RestUser(
                                realName = user.realName.value,
                                nickname = user.nickname.value,
                                follows = user.follows.map { it.value }
                        )
                    }
            call.respond(HttpStatusCode.OK, user.orNull()!!)
        }
        get("/users/{nickname}/follows") {
            val user = call.parameters["nickname"].orEmpty()
            logger.debug { "Retrieving list of follows for user $user" }
            val follows = koinProxy.get<AskFollows>().execute(Nickname(user))
            call.respond(HttpStatusCode.OK, follows)
        }
    }
    return parentRoute
}

internal fun tweetRoutes(parentRoute: Route) {
    parentRoute {
        post("/tweets") {
            val tweet = call.receive<RestTweet>()
            logger.debug { "Registering tweet $tweet" }
            koinProxy.get<SendTweet>().execute(tweet.toDomain())
            call.respond(HttpStatusCode.Created, "Tweet $tweet saved successfully")
        }
        get("/tweets/{nickname}") {
            val user = call.parameters["nickname"].orEmpty()
            logger.debug { "Retrieving list of tweets from user $user" }
            val tweets = koinProxy.get<ReadTweets>().execute(Nickname(user)).map {
                RestTweet(nickname = it.nickName.value, content = it.content.value)
            }
            call.respond(HttpStatusCode.OK, tweets)
        }
    }
}

internal data class Message(val value: String)

internal data class RestUser(val realName: String, val nickname: String, val follows: List<String>?) {
    fun toDomain(): User = User(
            realName = RealName(realName),
            nickname = Nickname(nickname),
            follows = follows?.map { Nickname(it) }?.toSet() ?: emptySet()
    )
}

internal data class UserFollow(val nickname: String)

internal data class RestTweet(val nickname: String, val content: String) {
    fun toDomain(): Tweet = Tweet(
            nickName = Nickname(nickname),
            content = TweetContent(content)
    )
}