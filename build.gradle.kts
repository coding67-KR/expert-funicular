plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "game.twentyquestions"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.genai:google-genai:1.67.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("game.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
