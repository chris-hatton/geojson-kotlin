import java.util.*

plugins {

    kotlin("jvm") version "1.3.21"
    id("fr.coppernic.versioning") version "3.1.2"
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("com.google.code.gson:gson:2.8.5")

    testCompile("junit:junit:4.12")
}

tasks {
    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
        moduleName = rootProject.name
    }

    publishToMavenLocal {
        dependsOn(build)
    }
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
    dependsOn(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

val artifactName = "kgeogson"
val artifactGroup = "fr.bipi.lib"

val pomUrl = "https://github.com/bastienpaulfr/geojson-kotlin"
val pomScmUrl = "https://github.com/bastienpaulfr/geojson-kotlin"
val pomIssueUrl = "https://github.com/bastienpaulfr/geojson-kotlin/issues"
val pomDesc = "Kotlin GeoJson by Gson"
val pomScmConnection = "scm:git:git://github.com/Coppernic/AndroidUtils"
val pomScmDevConnection = "scm:git:git://github.com/Coppernic/AndroidUtils"

val githubRepo = "bastienpaulfr/geojson-kotlin"
val githubReadme = "README.md"

val pomLicenseName = "The Apache Software License, Version 2.0"
val pomLicenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
val pomLicenseDist = "repo"

val pomDeveloperId = "bastienpaulfr"
val pomDeveloperName = "Bastien Paul"


publishing {
    publications {
        create<MavenPublication>("lib") {
            groupId = artifactGroup
            artifactId = artifactName
            version = project.versioning.info.display
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)

            pom.withXml {
                asNode().apply {
                    appendNode("description", pomDesc)
                    appendNode("name", rootProject.name)
                    appendNode("url", pomUrl)
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", pomLicenseName)
                        appendNode("url", pomLicenseUrl)
                        appendNode("distribution", pomLicenseDist)
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", pomDeveloperId)
                        appendNode("name", pomDeveloperName)
                    }
                    appendNode("scm").apply {
                        appendNode("url", pomScmUrl)
                        appendNode("connection", pomScmConnection)
                    }
                }
            }
        }
    }
}

bintray {
    user = if (project.hasProperty("bintray_user")) project.property("bintray_user") as String else ""
    key = if (project.hasProperty("bintray_key")) project.property("bintray_key") as String else ""
    publish = true

    setPublications("lib")

    pkg.apply {
        repo = "maven"
        name = rootProject.name
        setLicenses("Apache-2.0")
        setLabels("Gson", "json", "GeoJson", "GPS", "Kotlin")
        vcsUrl = pomScmUrl
        websiteUrl = pomUrl
        issueTrackerUrl = pomIssueUrl
        githubRepo = githubRepo
        githubReleaseNotesFile = githubReadme

        version.apply {
            name = project.versioning.info.display
            desc = pomDesc
            released = Date().toString()
            vcsTag = project.versioning.info.tag
        }
    }
}
