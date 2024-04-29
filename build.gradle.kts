plugins {
    kotlin("multiplatform") version "1.9.23"
}

kotlin {
    jvm()

    sourceSets {
        commonMain {
            dependencies {
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
    mavenCentral()
    google()
}

group = "com.github.cvb941.kchesslib"
version = "1.3.3"
description = "kchesslib"
