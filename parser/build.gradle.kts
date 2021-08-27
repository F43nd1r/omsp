plugins {
    kotlin("multiplatform") version "1.5.30"
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "1.5.0"
}

group = "com.faendir.om"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

kotlin {
    jvm()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api("com.squareup.okio:okio-multiplatform:3.0.0-alpha.9")
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val linuxX64Main by getting {
            dependencies {
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("org.hamcrest:hamcrest:2.2")
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    group = "documentation"
    from(tasks["dokkaHtml"])
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        mavenLocal()
    }
    publications.withType<MavenPublication> {
        artifact(javadocJar)
        pom {
            artifactId = "parser"
            name.set("om-parser")
            description.set("Opus Magnum Solution/Puzzle Parser")
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

signing {
    val signingKey = project.findProperty("signingKey") as? String
        ?: System.getenv("SIGNING_KEY")
    val signingPassword = project.findProperty("signingPassword") as? String
        ?: System.getenv("SIGNING_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}