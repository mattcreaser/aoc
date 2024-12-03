plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.common)
    testImplementation(libs.bundles.test)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}