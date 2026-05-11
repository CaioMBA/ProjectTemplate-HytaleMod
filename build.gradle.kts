/**
 * NOTE: This is entirely optional and basics can be done in `settings.gradle.kts`
 */

group = providers.gradleProperty("plugin_group").getOrElse("dev.ofatech")
version = providers.gradleProperty("plugin_version").getOrElse("0.1.0")

repositories {
    // Any external repositories besides: MavenLocal, MavenCentral, HytaleMaven, and CurseMaven
}

dependencies {
    // Any external dependency you also want to include
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testImplementation("org.mockito:mockito-core:5.12.0")
}

tasks.test {
    useJUnitPlatform()
}
