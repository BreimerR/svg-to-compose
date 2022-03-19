import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
    id("maven-publish")
}

group = "br.com.devsrsouza"
version = "0.7.0"

repositories {
    mavenCentral()
    google()
    maven("https://jitpack.io")
    maven("https://maven.google.com")
    maven("https://jetbrains.bintray.com/trove4j")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.android.tools:sdk-common:27.2.0-alpha16")
    implementation("com.android.tools:common:27.2.0-alpha16")
    implementation("com.squareup:kotlinpoet:1.9.0")
    implementation("org.ogce:xpp3:1.1.6")

    testImplementation(kotlin("test-junit"))
    implementation(compose.ui)
    testImplementation("org.jetbrains.compose.runtime:runtime:1.1.1")
    implementation(compose.foundation)
    implementation(compose.desktop.currentOs)

}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
        }
    }
}
