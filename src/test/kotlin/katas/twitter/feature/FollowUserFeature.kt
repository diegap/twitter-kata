package katas.twitter.feature

import arrow.core.Option
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import katas.twitter.actions.FollowUser
import katas.twitter.model.user.FollowedUser
import katas.twitter.model.user.Nickname
import katas.twitter.repositories.FollowedUserRepository
import org.mockito.Mockito.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object FollowUserFeature : Spek({

    Feature("Follow a user"){
        val user = FollowedUser(Nickname("@jack"), mutableSetOf())
        val follower = Nickname("@musk")

        Scenario("following an existing user"){
            val followedUserRepository = mock<FollowedUserRepository> {
                on { find(user.nickname) } doReturn Option.just(user)
                doNothing().`when`(it).save(user)
            }
            When("a user wants to follow another user"){
                val followUser = FollowUser(followedUserRepository)
                followUser.execute(user.nickname, follower)
            }
            Then("save is invoked"){
                verify(followedUserRepository, atMost(1)).find(user.nickname)
                verify(followedUserRepository, atMost(1)).save(user)
            }
        }


    }
})