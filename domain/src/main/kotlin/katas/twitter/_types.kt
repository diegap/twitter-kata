package katas.twitter

abstract class NonEmptyString(value: String){
    init {
        require(value.isNotBlank()){
            "Field value cannot be blank"
        }
    }
}