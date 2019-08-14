package katas.twitter

import arrow.core.Some
import katas.twitter.config.MongoConfig
import katas.twitter.model.user.Nickname
import katas.twitter.model.user.RealName
import katas.twitter.model.user.User
import katas.twitter.repositories.MongoUserRepository
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeGreaterThan
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.testcontainers.containers.GenericContainer

class KMongoContainer(imageName: String) : GenericContainer<KMongoContainer>(imageName)

internal class UserRepositoryTest {

    @get:Rule
    val mongodb: KMongoContainer = KMongoContainer("mongo:latest").withExposedPorts(27017)
    var mongoConfig: MongoConfig? = null

    @Before
    fun setup(){
        val address = mongodb.containerIpAddress
        val port = mongodb.getMappedPort(27017)

        address shouldNotBe null
        port shouldBeGreaterThan 0

        mongoConfig = MongoConfig(addresses = "$address:$port",
                userName = "",
                password = "",
                options = "",
                database = "twitter")
    }

    @Test
    fun testSavingUser(){

        // given
        val userRepository = MongoUserRepository(mongoConfig!!)

        // when
        val user = User(realName = RealName("Jack"), nickname = Nickname("@jack"), follows = emptySet())
        userRepository.save(user)

        // then
        val retrievedUser = userRepository.find(user.nickname)

        retrievedUser.isDefined() shouldBe true
        retrievedUser.map { it.nickname.value } shouldEqual Some("@jack")

    }

}