pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "aoc"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":y2023")
project(":y2023").projectDir = file("2023")
include(":y2024")
project(":y2024").projectDir = file("2024")
include(":common")