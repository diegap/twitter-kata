/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package katas.twitter

class App {
    val greeting: String
        get() {
            return "Hello Twitter kata!"
        }
}

fun main(args: Array<String>) {
    println(App().greeting)
}