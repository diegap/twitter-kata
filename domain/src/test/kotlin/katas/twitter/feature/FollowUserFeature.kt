package katas.twitter.feature

import arrow.core.Option
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import katas.twitter.actions.AskFollows
import katas.twitter.actions.FollowUser
import katas.twitter.model.user.Nickname
import katas.twitter.model.user.RealName
import katas.twitter.model.user.User
import katas.twitter.repositories.UserRepository
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldContain
import org.mockito.Mockito.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object FollowUserFeature : Spek({

    Feature("Follow a user"){
        val user = User(RealName("Jack Bauer"), Nickname("@jack"), setOf())
        val follow = Nickname("@musk")

        Scenario("following an existing user"){

            val userRepository = mock<UserRepository> {
                on { find(user.nickname) } doReturn Option.just(user)
                doNothing().`when`(it).save(user)
            }

            When("a user wants to follow another user"){
                val followUser = FollowUser(userRepository)
                followUser.execute(user.nickname, follow)
            }
            Then("save is invoked"){
                verify(userRepository, atMost(1)).find(user.nickname)
                argumentCaptor<User>().apply {
                    verify(userRepository, atMost(1)).save(capture())
                    firstValue.isFollowing(follow) `should be equal to` true
                }
            }
        }

        Scenario("asking who are followers of a given user"){
            var followers = setOf<Nickname>()
            When("anyone wants to know who are the followers of a user"){
                val userRepository = mock<UserRepository> {
                    on { find(user.nickname) } doReturn Option.just(user.copy(follows = setOf(follow)))
                }
                val askFollowers = AskFollows(userRepository)
                followers = askFollowers.execute(user.nickname)
            }
            Then("a list of followers is retrieved"){
                followers shouldContain follow
            }
        }
    }
})