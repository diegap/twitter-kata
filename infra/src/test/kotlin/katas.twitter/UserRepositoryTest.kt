package katas.twitter

import org.amshove.kluent.shouldBeGreaterThan
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.testcontainers.containers.GenericContainer

class KMongoContainer(imageName: String) : GenericContainer<KMongoContainer>(imageName)

internal class UserRepositoryTest {

    @get:Rule
    val mongodb: KMongoContainer = KMongoContainer("mongo:latest").withExposedPorts(27017)

    @Before
    fun setup(){
        val address = mongodb.containerIpAddress
        val port = mongodb.firstMappedPort

        address shouldNotBe null
        port shouldBeGreaterThan 0
    }

    @Test
    fun test(){

    }

}