val koinVersion = "2.0.1"
val config4kVersion = "0.3.4"
val logbackVersion = "1.2.3"
val kotlinLoggingVersion = "1.7.4"
val kmongoVersion = "3.10.2"
val testContainersVersion = "1.11.3"

dependencies {
    compile(project(":domain"))

    compile("org.koin:koin-core:$koinVersion")
    compile("io.github.config4k:config4k:$config4kVersion")
    compile("ch.qos.logback:logback-classic:$logbackVersion")

    compile("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    compile("org.litote.kmongo:kmongo:$kmongoVersion")
    compile("org.litote.kmongo:kmongo-id-jackson:$kmongoVersion")

    testCompile("org.koin:koin-test:$koinVersion")
    testCompile("org.testcontainers:testcontainers:$testContainersVersion")
}

tasks {
    test {
        useJUnit()
    }
}