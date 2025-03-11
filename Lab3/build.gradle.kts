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
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    runtimeOnly("org.xerial:sqlite-jdbc:3.49.1.0")

}

application{
    mainClass = "com/example/Main"
}

tasks.test {
    useJUnitPlatform()
}