package katas.twitter.infra.repositories

import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import katas.twitter.domain.model.tweet.Tweet
import katas.twitter.domain.model.tweet.TweetContent
import katas.twitter.domain.model.user.Nickname
import katas.twitter.domain.repositories.TweetRepository
import katas.twitter.infra.config.MongoConfig
import mu.KotlinLogging
import org.litote.kmongo.*

class MongoTweetRepository(private val mongoConfig: MongoConfig) : TweetRepository {

    private val database : MongoDatabase
    private val collection: MongoCollection<DBTweet>
    private val logger = KotlinLogging.logger {}

    companion object {
        private const val CollectionName  = "tweets"
    }

    init {
        val mongoUri = mongoConfig.toMongoUri()
        val client = KMongo.createClient(MongoClientURI(mongoUri))
        database = client.getDatabase(mongoConfig.database.trim())
        collection = database.getCollection(CollectionName, DBTweet::class.java)
    }

    override fun save(tweet: Tweet) =
            DBTweet.from(tweet).let {
                collection.save(it)
            }.also {
                logger.debug { "Tweet saved >> $tweet" }
            }

    override fun find(nickname: Nickname): Set<Tweet> =
            collection.find(DBTweet::nickname eq nickname.value)
            .map { it.toDomain() }
            .toSet()
}

internal data class DBTweet(val nickname: String?, val content: String?, val _id: Id<DBTweet> = newId()) {
    companion object {
        fun from(tweet: Tweet): DBTweet = DBTweet(tweet.nickName.value, tweet.content.value)
    }
    fun toDomain(): Tweet = Tweet(nickName = Nickname(this.nickname!!), content = TweetContent(this.content!!))
}
