import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("multiplatform") version "2.0.21"
    id("com.vanniktech.maven.publish") version "0.30.0"
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
    macosX64()
    macosArm64()
    mingwX64()
    linuxX64()
    linuxArm64()

    js {
        browser()
        nodejs()
    }
    wasmJs()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
                implementation("com.benasher44:uuid:0.8.4")
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.5.4")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
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

group = "io.github.cvb941"
version = "1.0.1"
description = "kchesslib"

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = false)

    signAllPublications()

    pom {
        name.set("kchesslib")
        description.set("Chess library for legal move generation, FEN/PGN parsing and more")
        inceptionYear.set("2024")
        url.set("https://github.com/cvb941/kchesslib/")
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0")
            }
        }
        developers {
            developer {
                id.set("cvb941")
                name.set("Lukas Kusik")
                url.set("https://github.com/cvb941/")
            }
        }
        scm {
            url.set("https://github.com/cvb941/kchesslib/")
            connection.set("scm:git:git://github.com/cvb941/kchesslib.git")
            developerConnection.set("scm:git:ssh://git@github.com/cvb941/kchesslib.git")
        }
    }
}
