plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}