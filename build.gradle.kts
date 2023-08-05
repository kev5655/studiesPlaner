import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val ktorVersion: String = "2.3.2"
val junitVersion: String = "5.7.1"

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "me.kevin.zahn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainKt")
}