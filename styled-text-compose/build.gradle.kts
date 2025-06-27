import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
    id ("com.vanniktech.maven.publish") version "0.28.0"
}

android {
    namespace = "dev.serious.styledtext"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        aarMetadata {
            minCompileSdk = 21
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.compose.ui)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.foundation)
    implementation(libs.material3)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
mavenPublishing {
    coordinates(
        groupId = "io.github.serioushyeon",
        artifactId = "styled-text-compose",
        version = "1.0.0"
    )

    pom {
        name.set("Styled Text Compose")
        description.set("A convenient library for styling text in Jetpack Compose with AnnotatedString")
        inceptionYear.set("2025")
        url.set("https://github.com/serioushyeon/styled-text-compose")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("serioushyeon")
                name.set("Jihyeon Jin")
                email.set("zz64446949@gmail.com")
                url.set("https://github.com/serioushyeon/")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/serioushyeon/styled-text-compose.git")
            developerConnection.set("scm:git:ssh://github.com/serioushyeon/styled-text-compose.git")
            url.set("https://github.com/serioushyeon/styled-text-compose/tree/main")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}