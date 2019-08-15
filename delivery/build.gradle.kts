import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val ktorVersion = "1.2.3"
val jacksonVersion = "2.9.9"
val config4kVersion = "0.3.4"

plugins {
    id("com.github.johnrengelman.shadow") version "5.0.0"
}
dependencies {
    compile(project(":domain"))
    compile(project(":infra"))

    compile("io.github.config4k:config4k:$config4kVersion")

    compile("io.ktor:ktor-server-core:$ktorVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    compile("io.ktor:ktor-client-core:$ktorVersion")
    compile("io.ktor:ktor-client-apache:$ktorVersion")
    compile("io.ktor:ktor-client-logging:$ktorVersion")
    compile("io.ktor:ktor-client-json:$ktorVersion")
    compile("io.ktor:ktor-client-jackson:$ktorVersion")
    compile("io.ktor:ktor-jackson:$ktorVersion")

    implementation("io.ktor:ktor-client-jackson:$ktorVersion")

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