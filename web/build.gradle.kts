import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    // Always include JVM target for configuration regardless of JS being enabled
    jvm()

    // Only configure JS if the feature flag is enabled
    if (project.findProperty("kotlin.js.enabled")?.toString() != "false") {
        js(IR) {
            browser {
                testTask {
                    testLogging.showStandardStreams = true
                    useKarma {
                        useChromeHeadless()
                        useFirefox()
                    }
                }
            }
            binaries.executable()
        }

        // Only configure NodeJsRootExtension if JS is enabled
        afterEvaluate {
            rootProject.extensions.configure<NodeJsRootExtension> {
        //        versions.webpackDevServer.version = "4.0.0"
        //        versions.webpackCli.version = "4.9.0"
        //        nodeVersion = "16.0.0"
            }
        }

        sourceSets {
            val jsMain by getting {
                dependencies {
                    implementation(compose.html.core)
                    implementation(compose.runtime)
                    implementation(compose.material)
                    implementation(compose.materialIconsExtended)
                    implementation(project(":reorderable"))
                }
            }
            val jsTest by getting {
                dependencies {
                    implementation(kotlin("test-js"))
                }
            }
        }
    }

    // Add minimal sourceSets for JVM target
    sourceSets {
        val jvmMain by getting {
            dependencies {
                // Minimal dependencies for JVM target
                implementation(compose.desktop.common)
            }
        }
    }
}
