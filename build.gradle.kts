plugins {

    kotlin("jvm") version "1.7.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://nexus.unwire.com/repository/unwire-maven/")
        credentials {
            username = "unwire-gradle"
            password = "6vHMx9fLWV6zb9x7"
        }
    }
}

dependencies {

    implementation("io.rest-assured:rest-assured:5.3.0")

    implementation("io.cucumber:cucumber-java:7.11.1")
    implementation("io.cucumber:cucumber-junit:7.11.1")
    implementation("io.rest-assured:json-schema-validator:5.3.1")
    implementation("io.rest-assured:kotlin-extensions:5.3.0")
    implementation("io.cucumber:cucumber-picocontainer:7.13.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.platform:junit-platform-suite-engine:1.10.0")


    implementation("tech.grasshopper:extentreports-cucumber7-adapter:1.13.0")
    implementation ("com.aventstack:extentreports:5.0.9")
    implementation("io.cucumber:gherkin:26.2.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}