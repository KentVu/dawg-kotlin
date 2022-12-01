plugins {
   kotlin("multiplatform")
}

group = 'org.chalup'
version = '1.0-SNAPSHOT'
/*
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

kotlin {
    jvm()
    js(IR) {
        browser()
    }

    sourceSets {
        named("commonMain") {
            api("com.squareup.okio:okio:2.3.0")
            implementation "com.beust:jcommander:1.78"
        }
    }
}