plugins {
    kotlin("jvm") version "1.9.23"
}

repositories {
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
    google()
}

dependencies {
    api("org.apache.commons:commons-lang3:3.12.0")
    testImplementation("junit:junit:4.13.1")
}

group = "com.github.cvb941.kchesslib"
version = "1.3.3"
description = "kchesslib"
