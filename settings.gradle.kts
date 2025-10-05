pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Benedikto One"
include(":app")
include(":feature-auth")
include(":feature-dashboard")
include(":feature-orders")
include(":feature-clients")
include(":feature-products")
include(":data")
include(":core-ui")
include(":core-navigation")
