package katas.twitter.model.user

data class FollowedUser(val nickname: Nickname, val followers: Set<Nickname>){
    fun isFollowedBy(nickname: Nickname) = followers.contains(nickname)
}