import org.jetbrains.compose.ComposeBuildConfig.composeVersion

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("maven-publish")
    id("signing")
}

group = "com.github.projectdelta6"
version = "0.9.8"

kotlin {
    jvm()
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation("org.jetbrains.compose.ui:ui-util:${composeVersion}")
            }
        }
    }
}

val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    archiveClassifier.set("javadoc")
}

publishing {
//    publications {
//        create<MavenPublication>("release") {
//            afterEvaluate {
//                from(components["release"])
//            }
//            groupId = "com.github.projectdelta6"
//            artifactId = project.name
//            version = "0.9.8"
//        }
//    }
//    publications {
//        repositories {
//            maven {
//                name="oss"
//                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
//                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
//                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
//                credentials {
//                    username = extra.properties.getOrDefault("ossrh.Username", "") as String
//                    password = extra.properties.getOrDefault("ossrh.Password", "") as String
//                }
//            }
//        }
//    }
    publications {
        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("ComposeReorderable")
                description.set("Reorderable Compose LazyList")
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }
                url.set("https://github.com/projectdelta6/ComposeReorderable")
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/projectdelta6/ComposeReorderable/issues")
                }
                scm {
                    connection.set("https://github.com/projectdelta6/ComposeReorderable.git")
                    url.set("https://github.com/projectdelta6/ComposeReorderable")
                }
                developers {
                    developer {
                        name.set("Andre Claßen")
                        email.set("andreclassen1337@gmail.com")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications)
}
