plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kspSupport)
}

android {
    namespace = "dev.daniza.portfoliowatcher"
    //noinspection GradleDependency
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.daniza.portfoliowatcher"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }

        create("staging") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".debugStaging"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        lintConfig = file("$rootDir/lint.xml")
        abortOnError = true
    }
    lint.warningsAsErrors = true
}

dependencies {
    implementation(project(":remote"))
    implementation(project(":core"))
    implementation(project(":presenter"))
    implementation(project(":ui"))
    implementation(project(":model"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.timber.core)
}