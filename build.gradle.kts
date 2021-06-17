plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
    id("fr.brouillard.oss.gradle.jgitver") version "0.10.0-rc03"
}

group = "com.faendir.om"

nexusPublishing {
    repositories {
        sonatype {
            username.set(project.findProperty("ossrhUser") as? String ?: System.getenv("OSSRH_USER"))
            password.set(project.findProperty("ossrhPassword") as? String ?: System.getenv("OSSRH_PASSWORD"))
        }
    }
}

tasks.register("publish") {
    group = "publishing"
    subprojects {
        tasks.findByName("publish")?.let { dependsOn(it) }
        tasks.findByName("publishToSonatype")?.let { dependsOn(it) }
    }
    dependsOn("closeAndReleaseSonatypeStagingRepository")
}