plugins {
    kotlin("multiplatform") version "1.9.23"
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain {
            dependencies {
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        jvmTest {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
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
