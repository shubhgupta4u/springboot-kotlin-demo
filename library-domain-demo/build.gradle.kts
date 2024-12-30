plugins {
    kotlin("jvm") version "2.0.21"
    id("org.springframework.boot") version "3.4.1" // Spring Boot plugin
    id("io.spring.dependency-management") version "1.1.0" // Spring dependency manageme
    id("maven-publish") // Maven Publish plugin
}
group = "library.domain.demo"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_22

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot JPA dependency (includes Hibernate)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Jakarta Persistence API dependency
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // Hibernate ORM as JPA provider
    implementation("org.hibernate.orm:hibernate-core:6.0.0.Final")

    // MySQL JDBC driver for connecting to MySQL database
    implementation("com.mysql:mysql-connector-j:9.1.0")

    // Spring Boot Validation Starter
    implementation("org.springframework.boot:spring-boot-starter-validation") // Validation support

    // Kotlin standard library
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // This refers to the main Java component

            // Define artifactId, version, and group
            groupId = "library.domain.demo" // Replace with your group
            artifactId = "library-domain" // Replace with your library name
            version = "1.0.0" // Replace with your version
        }
    }
}
