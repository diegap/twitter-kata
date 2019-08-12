package katas.twitter.feature

import arrow.core.Option
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import katas.twitter.actions.AskFollows
import katas.twitter.model.user.FollowedUser
import katas.twitter.model.user.Nickname
import katas.twitter.repositories.FollowedUserRepository
import org.amshove.kluent.shouldBe
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object AskFollowFeature : Spek({

    val follower = Nickname("@musk")
    var user = FollowedUser(Nickname("@jack"), mutableSetOf(follower))

    Feature("Check follows"){

        Scenario("Checking user is followed"){
            val followedUserRepository = mock<FollowedUserRepository> {
                on { find(user.nickname) } doReturn Option.just(user.copy(followers = user.followers.plusElement(follower)))
            }
            When("A user wants to know if is being followed"){
                val askFollows = AskFollows(followedUserRepository)
                user = askFollows.execute(user.nickname).orNull()!!
            }
            Then("follower is contained in user followers"){
                user.isFollowedBy(follower) shouldBe true
            }
        }
    }
})

