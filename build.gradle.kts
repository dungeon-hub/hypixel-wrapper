import net.thebugmc.gradle.sonatypepublisher.PublishingType

plugins {
    id("java-library")
    id("net.thebugmc.gradle.sonatype-central-portal-publisher").version("1.2.3")
    kotlin("jvm") version "2.0.21"
}

group = "net.dungeon-hub"
val artifactId = "hypixel-wrapper"
version = "0.4.1"
description = "A simple Kotlin wrapper for the Hypixel API, including a cache."

repositories {
    maven {
        url = uri("https://repo.hypixel.net/repository/Hypixel/")
        name = "Hypixel Repository"
    }

    mavenCentral()
}

dependencies {
    //Functionality
    api("org.jsoup:jsoup:1.15.3")
    api("net.hypixel:hypixel-api-core:4.4")
    api("me.nullicorn:Nedit:2.2.0")

    //HTTP Client
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    api("io.ktor:ktor-client-okhttp:3.0.0")

    //Logging
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")

    //Testing
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(kotlin("test"))
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
}

centralPortal {
    name = artifactId
    publishingType = PublishingType.USER_MANAGED

    pom {
        name = artifactId
        description = project.description
        url = "https://github.com/dungeon-hub/hypixel-wrapper"

        organization {
            name = "Dungeon Hub"
            url = "https://dungeon-hub.net/"
        }

        scm {
            url = "https://github.com/dungeon-hub/hypixel-wrapper"
            connection = "scm:git://github.com:dungeon-hub/hypixel-wrapper.git"
            developerConnection = "scm:git://github.com:dungeon-hub/hypixel-wrapper.git"
        }

        developers {
            developer {
                id = "taubsie"
                name = "Taubsie"
                email = "taubsie@dungeon-hub.net"
                url = "https://github.com/Taubsie/"
                organizationUrl = "https://dungeon-hub.net/"
            }
        }

        licenses {
            license {
                name = "GPL-3.0"
                url = "https://www.gnu.org/licenses/gpl-3.0"
                distribution = "https://www.gnu.org/licenses/gpl-3.0.txt"
            }
        }
    }
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}
