package katas.twitter.repositories

import arrow.core.Option
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import katas.twitter.config.MongoConfig
import katas.twitter.model.user.Nickname
import katas.twitter.model.user.RealName
import katas.twitter.model.user.User
import mu.KotlinLogging
import org.litote.kmongo.*

internal class MongoUserRepository(mongoConfig: MongoConfig) : UserRepository {

    private val database : MongoDatabase
    private val collection: MongoCollection<DBUser>
    private val logger = KotlinLogging.logger {}

    companion object {
        private const val CollectionName  = "users"
    }

    init {
        val mongoUri = mongoConfig.toMongoUri()
        val client = KMongo.createClient(MongoClientURI(mongoUri))
        database = client.getDatabase(mongoConfig.database.trim())
        collection = database.getCollection(CollectionName, DBUser::class.java)
        collection.ensureIndex("{'nickname':1,'type':1}")
    }

    override fun find(nickname: Nickname): Option<User> =
            Option.fromNullable(collection.findOne(DBUser::nickname eq nickname.value)?.toDomain())

    override fun save(user: User) {
        val dbUser = DBUser.from(user)
        if (collection.findOneAndReplace(DBUser::nickname eq dbUser.nickname, dbUser) == null){
            collection.save(dbUser).also { logger.debug { "User >> $user created" } }
        } else {
            collection.updateOneById(dbUser.nickname, dbUser).also { logger.debug { "User >> $user updated" } }
        }
    }
}

data class DBUser(val realName: String, val nickname: String, val follows: Set<String>) {

    fun toDomain(): User {
        return User(realName = RealName(this.realName),
                nickname = Nickname(this.nickname),
                follows = this.follows.map { Nickname(it) }.toSet())
    }

    companion object {
        fun from(user: User): DBUser {
            return DBUser(
                    realName = user.realName.value,
                    nickname = user.nickname.value,
                    follows = user.follows.map { it.value }.toSet()
            )
        }
    }
}