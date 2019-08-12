package katas.twitter.feature

import arrow.core.Option
import com.nhaarman.mockitokotlin2.*
import katas.twitter.actions.RegisterUser
import katas.twitter.model.user.Nickname
import katas.twitter.model.user.RealName
import katas.twitter.model.user.User
import katas.twitter.repositories.UserRepository
import org.mockito.Mockito.doNothing
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertFailsWith

object RegisterUserFeature : Spek({

    Feature("Register a user"){
        val user = User(RealName("Jack Bauer"), Nickname("@jack"), setOf())

        Scenario("registering a non existing user"){
            val userRepository = mock<UserRepository> {
                on { find(user.nickname) } doReturn Option.empty()
                doNothing().`when`(it).save(user)
            }

            When("a user is registered for the first time"){
                val registerUser = RegisterUser(userRepository)
                registerUser.execute(user)
            }
            Then("a new user is created"){
                verify(userRepository, atMost(1)).save(user)
            }
        }

        Scenario("registering an already registered user"){
            val userRepository = mock<UserRepository> {
                on { find(user.nickname) } doReturn Option.just(user)
            }

            When("a user tries to register with an existing nickname"){
                val registerUser = RegisterUser(userRepository)
                assertFailsWith<RuntimeException> { registerUser.execute(user) }
            }
            Then("save is not invoked"){
                verify(userRepository, never()).save(any())
            }
        }
    }
})