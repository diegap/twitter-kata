import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val arrowVersion = "0.8.2"
val spekVersion = "2.0.6"
val mockitoVersion = "2.1.0"
val kluentVersion = "1.49"

plugins {
    base
    idea
    kotlin("jvm") version "1.3.41"
}

repositories {
    mavenCentral()
    jcenter()
}

allprojects {
    group = "katas.twitter"
}

subprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        // Use the Kotlin JDK 8 standard library.
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("io.arrow-kt:arrow-core:$arrowVersion")

        // Use the Kotlin test library.
        testImplementation("org.jetbrains.kotlin:kotlin-test")

        testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
        testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
        testImplementation("org.amshove.kluent:kluent:$kluentVersion")

        // Use the Kotlin JUnit integration.
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
        testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoVersion")
    }

     tasks {
        withType<Test> {
            useJUnitPlatform {
                includeEngines("spek2")
            }
            testLogging {
                    events = TestLogEvent.values().toSet()
            }
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
}