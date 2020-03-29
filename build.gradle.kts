plugins {
    kotlin("jvm") version "1.3.71"
    `maven-publish`
}

group = "com.faendir.om"
version = "1.3.4"

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

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
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
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            artifact(sourcesJar)
            pom {
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
}