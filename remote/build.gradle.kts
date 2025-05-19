plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kspSupport)
}

android {
    namespace = "dev.daniza.portfoliowatcher.remote"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "API_KEY", project.properties["API_KEY_TOKENMETRICS"].toString())
        buildConfigField("String", "API_BASE_URL", project.properties["API_BASE_URL"].toString())
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":model"))
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.workmanager)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.logging)
    implementation(libs.gson)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
}