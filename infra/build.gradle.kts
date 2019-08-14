import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val koinVersion = "2.0.1"
val ktorVersion = "1.2.3"
val jacksonVersion = "2.9.9"
val config4kVersion = "0.3.4"
val logbackVersion = "1.2.3"
val kotlinLoggingVersion = "1.7.4"
val kmongoVersion = "3.10.2"
val testContainersVersion = "1.11.3"

plugins {
    id("com.github.johnrengelman.shadow") version "5.0.0"
}
dependencies {
    compile(project(":domain"))

    compile("org.koin:koin-core:$koinVersion")
    compile("io.github.config4k:config4k:$config4kVersion")
    compile("ch.qos.logback:logback-classic:$logbackVersion")

    compile("io.ktor:ktor-server-core:$ktorVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    compile("io.ktor:ktor-client-core:$ktorVersion")
    compile("io.ktor:ktor-client-apache:$ktorVersion")
    compile("io.ktor:ktor-client-logging:$ktorVersion")
    compile("io.ktor:ktor-client-json:$ktorVersion")
    compile("io.ktor:ktor-client-jackson:$ktorVersion")
    compile("io.ktor:ktor-jackson:$ktorVersion")

    implementation("io.ktor:ktor-client-jackson:$ktorVersion")

    compile("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    compile("org.litote.kmongo:kmongo:$kmongoVersion")
    compile("org.litote.kmongo:kmongo-id-jackson:$kmongoVersion")

    testCompile("org.koin:koin-test:$koinVersion")
    testCompile("org.testcontainers:testcontainers:$testContainersVersion")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("twitter-kata")
        archiveClassifier.set("")
        archiveVersion.set("")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "katas.twitter.AppKt"))
        }
    }
    test {
        useJUnit()
    }
    build {
        dependsOn(shadowJar)
    }
}