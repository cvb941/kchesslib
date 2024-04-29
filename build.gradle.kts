plugins {
    kotlin("multiplatform") version "1.9.23"
}

kotlin {
    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api("org.apache.commons:commons-lang3:3.12.0")
            }
        }
        jvmTest {
            dependencies {
               implementation("junit:junit:4.13.1")
            }
        }
    }
}

repositories {
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
    google()
}

group = "com.github.cvb941.kchesslib"
version = "1.3.3"
description = "kchesslib"
