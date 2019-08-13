import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val koinVersion = "1.0.2"
val ktorVersion = "1.1.4"
val kmongoVersion = "3.10.2"
val testContainersVersion = "1.11.3"

plugins {
    id("com.github.johnrengelman.shadow") version "5.0.0"
}
dependencies {
    compile(project(":domain"))
    compile("org.koin:koin-core:$koinVersion")
    compile("io.ktor:ktor-server-core:$ktorVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    compile("io.ktor:ktor-client-core:$ktorVersion")
    compile("io.ktor:ktor-client-apache:$ktorVersion")
    compile("io.ktor:ktor-client-logging:$ktorVersion")
    compile("io.ktor:ktor-client-json:$ktorVersion")
    compile("io.ktor:ktor-client-jackson:$ktorVersion")
    compile("io.ktor:ktor-jackson:$ktorVersion")
    compile("org.litote.kmongo:kmongo:$kmongoVersion"){
        exclude(group = "com.fasterxml.jackson")
    }
    compile("org.litote.kmongo:kmongo-id-jackson:$kmongoVersion"){
        exclude(group = "com.fasterxml.jackson")
    }

    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
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

    build {
        dependsOn(shadowJar)
    }
}