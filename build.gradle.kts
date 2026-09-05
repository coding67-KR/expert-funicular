plugins {
    kotlin("jvm") version "2.4.10"
    application
}

group = "game.twentyquestions"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.genai:google-genai-kotlin:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
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
