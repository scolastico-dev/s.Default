import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("org.jetbrains.dokka") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    kotlin("jvm") version "1.6.0"
    application
}

group = "me.scolastico" // HERE is configuration needed!
version = "dev-snapshot"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "12"
}

application {
    mainClass.set("me.scolastico.example.Application") // HERE is configuration needed!
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.6.0")
    }
}

apply(plugin="org.jetbrains.dokka")

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    jar{
        manifest {
            attributes["Main-Class"] = "me.scolastico.example.Application" // HERE is configuration needed!
        }
        archiveFileName.set("example.jar") // HERE is configuration needed!
    }
    shadowJar{
        archiveBaseName.set("example-shadow") // HERE is configuration needed!
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        txt.required.set(true)
        xml.required.set(false)
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "12"
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "12"
}

dependencies {
    testImplementation(kotlin("test"))

    // WebServer
    //implementation("io.ktor:ktor-server-netty:1.6.4")
    //implementation("io.ktor:ktor-html-builder:1.6.4")
    //implementation("io.ktor:ktor-websockets:1.6.4")
    //implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")

    // Default dependencies
    implementation("me.scolastico:tools:1.5.2")
    implementation("io.leego:banana:2.1.0")
}
