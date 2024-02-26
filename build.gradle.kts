plugins {
    alias(libs.plugins.kotlin.multiplatform)
//    application
}

kotlin {
    jvm {
        testRuns["test"].executionTask.configure {
            useTestNG()
//            useJUnitPlatform()
        }
    }
    /*js(IR) {
        browser()
    }*/

    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.okio)
            }
        }
//        val commonTest by getting {
//            dependencies {
//                implementation(kotlin("test")) // This brings all the platform dependencies automatically
//            }
//        }
        named("jvmMain") {
            dependencies {
                implementation("com.beust:jcommander:1.78")
            }
        }
        named("jvmTest") {
            dependencies {
                implementation(kotlin("test")) // This brings all the platform dependencies automatically
                implementation("com.google.truth:truth:1.1.3")
            }
        }
    }
}

//application {
//    mainClass.set("org.chalup.dawg.DawgGeneratorKt")
//}

/*
group = "org.chalup"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    api "com.squareup.okio:okio:2.3.0"
    implementation "com.beust:jcommander:1.78"

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.1")
    testImplementation 'com.google.truth:truth:1.0'
}

test {
    useJUnitPlatform()
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
*/
