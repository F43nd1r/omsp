plugins {
    kotlin("jvm") version "1.3.61"
    `maven-publish`
}

group = "com.faendir.omsp"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { setUrl("https://dl.bintray.com/juanchosaravia/autodsl") }

}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
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
            from(components["java"])
            artifact(sourcesJar.get())
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