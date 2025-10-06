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
include(":feature_auth")
include(":feature_dashboard")
include(":feature_orders")
include(":feature_clients")
include(":feature_products")
include(":data")
include(":core-ui")
include(":core-navigation")
include(":core-network")
include(":core-database")
