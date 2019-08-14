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
    compile("org.litote.kmongo:kmongo:$kmongoVersion")
    compile("org.litote.kmongo:kmongo-id-jackson:$kmongoVersion")
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