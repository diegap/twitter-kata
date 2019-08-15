package katas.twitter.domain

abstract class NonEmptyString(value: String){
    init {
        require(value.isNotBlank()){
            "Field value cannot be blank"
        }
    }
}