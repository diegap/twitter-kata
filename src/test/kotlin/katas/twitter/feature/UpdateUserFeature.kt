package katas.twitter.feature

import arrow.core.Option
import com.nhaarman.mockitokotlin2.*
import katas.twitter.actions.UpdateUser
import katas.twitter.model.user.Nickname
import katas.twitter.model.user.RealName
import katas.twitter.model.user.User
import katas.twitter.repositories.UserRepository
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertFailsWith

object UpdateUser : Spek({

    Feature("Update a user"){
        val user = User(RealName("Jack Bauer"), Nickname("@jack"), setOf())

        Scenario("updating real name of an existing user"){
            val userRepository = mock<UserRepository> {
                on { find(user.nickname) } doReturn Option.just(user)
            }

            When("a user tries to update his/her real name"){
                val updateUser = UpdateUser(userRepository)
                updateUser.execute(user.copy(realName = RealName(("Joaquin Baez"))))
            }

            Then("the user is updated"){
                verify(userRepository, atMost(1)).find(user.nickname)
                verify(userRepository, atMost(1)).save(user)
            }
        }

        Scenario("trying to update real name of a un-existing user"){
            val userRepository = mock<UserRepository> {
                on { find(user.nickname) } doReturn Option.empty()
            }
            When("a un-existing user tries to update his/her real name"){
                val updateUser = UpdateUser(userRepository)
                assertFailsWith<RuntimeException> {
                    updateUser.execute(user.copy(realName = RealName(("Joaquin Baez"))))
                }
            }
            Then("save is never invoked"){
                verify(userRepository, atMost(1)).find(user.nickname)
                verify(userRepository, never()).save(user)
            }
        }
    }
})