import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kspSupport)
}

android {
    namespace = "dev.daniza.portfoliowatcher.remote"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "TAG", "\"Remote\"")
        buildConfigField("String", "API_KEY_TOKENMETRICS", project.properties["API_KEY_TOKENMETRICS"].toString())
        buildConfigField("String", "API_URL_TOKENMETRICS", project.properties["API_BASE_URL"].toString())
        buildConfigField("String", "API_KEY_MORALIS", project.properties["API_KEY_MORALIS"].toString())
        buildConfigField("String", "API_URL_MORALIS", project.properties["API_BASE_URL_MORALIS"].toString())
        buildConfigField("String", "API_KEY_NEWSAPI", project.properties["API_KEY_NEWSAPI"].toString())
        buildConfigField("String", "API_URL_NEWSAPI", project.properties["API_BASE_URL_NEWSAPI"].toString())
        buildConfigField("String", "AUTHORIZATION_SELFHOST", project.properties["AUTHORIZATION_SELFHOST"].toString())
        buildConfigField("String", "API_URL_SELFHOST", project.properties["API_BASE_URL_SELFHOST"].toString())
        buildConfigField("String", "API_MARKET_PORT_SELFHOST", project.properties["API_MARKET_PORT_SELFHOST"].toString())
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
    kotlin {
        compilerOptions{
            optIn.add("kotlin.RequiresOptIn")
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":model"))
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.workmanager)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.logging)
    implementation(libs.gson)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.retrofit.mockwebserver)
}