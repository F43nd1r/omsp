plugins {
    kotlin("multiplatform") version "1.9.22"
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "1.9.10"
}

group = "com.faendir.om"

kotlin {
    jvm()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("com.squareup.okio:okio:3.7.0")
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

publishing {
    repositories {
        mavenLocal()
    }
    publications.withType<MavenPublication> {
        val publication = this
        val dokkaJar = tasks.register<Jar>("${publication.name}DokkaJar") {
            group = JavaBasePlugin.DOCUMENTATION_GROUP
            description = "Assembles Kotlin docs with Dokka into a Javadoc jar"
            archiveClassifier.set("javadoc")
            from(tasks.dokkaHtml)
            archiveBaseName.set("${archiveBaseName.get()}-${publication.name}")
        }
        artifact(dokkaJar)

        pom {
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