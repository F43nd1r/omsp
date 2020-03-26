plugins {
    kotlin("multiplatform") version "1.3.70"
    `maven-publish`
}

group = "com.faendir.om"
version = "1.1.3"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    js()
    val kotlinxioVersion = "0.1.16"
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api("org.jetbrains.kotlinx:kotlinx-io:$kotlinxioVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                api("org.jetbrains.kotlinx:kotlinx-io-jvm:$kotlinxioVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.hamcrest:hamcrest:2.2")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                api("org.jetbrains.kotlinx:kotlinx-io-js:$kotlinxioVersion")
            }
        }
    }
}

dependencies {
    //testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
}

publishing {
    repositories {
        mavenLocal()
        maven {
            setUrl("https://api.bintray.com/maven/f43nd1r/maven/omsp/;publish=1")
            name = "bintray"
            credentials {
                username = findProperty("artifactoryUser") as String?
                password = findProperty("artifactoryApiKey") as String?
            }
        }
    }
    publications.filterIsInstance<MavenPublication>().forEach {
        it.pom {
            name.set("omsp")
            description.set("Opus Magnum Solution Parser")
            url.set("https://github.com/F43nd1r/omsp")

            scm {
                connection.set("scm:git:https://github.com/F43nd1r/omsp.git")
                developerConnection.set("scm:git:git@github.com:F43nd1r/omsp.git")
                url.set("https://github.com/F43nd1r/omsp.git")
            }

            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    distribution.set("repo")
                }
            }

            developers {
                developer {
                    id.set("f43nd1r")
                    name.set("Lukas Morawietz")
                }
            }
        }

    }
}