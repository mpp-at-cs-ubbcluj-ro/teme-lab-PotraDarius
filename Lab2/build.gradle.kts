plugins {
    id("java")
    id("application")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api
    implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:33.4.0-jre")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j2-impl
    testImplementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3")


}

application{
    mainClass = "com/example/ComputerShopMain"
}

tasks.jar{
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest{
        attributes(
            "Main-Class" to "com.example.ComputerShopMain"
        )

    }
    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}

tasks.test {
    useJUnitPlatform()
}