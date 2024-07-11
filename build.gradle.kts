plugins {
    kotlin("multiplatform") version "2.0.0"
    id("maven-publish")
}

group = "com.github.cdietze.parsek"
version = "0.2.2"

repositories {
    mavenCentral()
}

kotlin {
    androidNativeX64()
    androidNativeX86()
    androidNativeArm32()
    androidNativeArm64()
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    linuxArm64()
    linuxX64()
    macosArm64()
    macosX64()
    mingwX64()
    tvosArm64()
    tvosSimulatorArm64()
    wasmJs()
    wasmWasi()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    watchosX64()

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.majorVersion
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport {
                        enabled = true
                    }
                }
            }
        }
    }

    sourceSets {
        commonMain {}
        commonTest.dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }
        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
            implementation("org.junit.jupiter:junit-jupiter-api:5.10.3")
            runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.3")
        }
        jsTest.dependencies {
            implementation(kotlin("test-js"))
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(javadocJar.get())

            artifactId = "parsek"
            groupId = project.group.toString()
            version = project.version.toString()

            pom {
                name.set("parsek")
            }
        }
    }
}
