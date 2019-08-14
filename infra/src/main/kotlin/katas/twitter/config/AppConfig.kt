package katas.twitter.config

data class MongoConfig(
        val addresses: String,
        val database: String,
        val userName: String?,
        val password: String?,
        val options: String?
) {
    init {
        require(addresses.isNotBlank()){
            "Addresses cannot be empty"
        }
        require(database.isNotBlank()){
            "Database cannot be empty"
        }
        if (userName != null && userName.isNotBlank()){
            requireNotNull(password)
            require(password.isNotBlank()){
                "Password cannot be empty"
            }
        }
    }

    fun toMongoUri() : String {
        val uriBuilder = StringBuilder("mongodb://")
        if (userName != null && userName.isNotBlank()) {
            uriBuilder.append(userName.trim())
            uriBuilder.append(":")
            uriBuilder.append(password!!.trim())
            uriBuilder.append("@")
        }
        uriBuilder.append(addresses)
        uriBuilder.append("/")
        uriBuilder.append(database.trim())
        if (options != null && options.isNotBlank()) {
            uriBuilder.append("?")
            uriBuilder.append(options.trim())
        }
        return uriBuilder.toString()
    }
}