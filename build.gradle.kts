plugins {
    kotlin("jvm") version "1.4.32"
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "1.4.32"
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
}

group = "com.faendir.om"
version = "2.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlinx:kotlinx-io-jvm:0.1.16")
    testImplementation(kotlin("test-junit"))
    testImplementation("org.hamcrest:hamcrest:2.2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    group = "documentation"
    from(tasks["dokkaJavadoc"])
    archiveClassifier.set("javadoc")
}

val sourcesJar = tasks.register<Jar>("sourcesJar") {
    group = "documentation"
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

publishing {
    repositories {
        mavenLocal()
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            artifact(sourcesJar)
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
}

signing {
    val signingKey = project.findProperty("signingKey") as? String ?: System.getenv("SIGNING_KEY")
    val signingPassword = project.findProperty("signingPassword") as? String ?: System.getenv("SIGNING_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["maven"])
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(project.findProperty("ossrhUser") as? String ?: System.getenv("OSSRH_USER"))
            password.set(project.findProperty("ossrhPassword") as? String ?: System.getenv("OSSRH_PASSWORD"))
        }
    }
}

tasks.getByName("publish") {
    dependsOn("publishToSonatype", "closeAndReleaseSonatypeStagingRepository")
}