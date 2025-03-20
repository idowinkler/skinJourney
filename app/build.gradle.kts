plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    id("kotlin-kapt")
    alias(libs.plugins.google.services)
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.example.skinJourney"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.skinJourney"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLOUDINARY_CLOUD_NAME", "\"${project.properties["CLOUDINARY_CLOUD_NAME"] ?: ""}\"")
        buildConfigField("String", "CLOUDINARY_API_KEY", "\"${project.properties["CLOUDINARY_API_KEY"] ?: ""}\"")
        buildConfigField("String", "CLOUDINARY_API_SECRET", "\"${project.properties["CLOUDINARY_API_SECRET"] ?: ""}\"")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        languageVersion = "2.1"
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.cloudinary.android)
    implementation(libs.glide)
    implementation(libs.androidx.constraintlayout.v214)
    implementation(libs.androidx.navigation.fragment)
    implementation("androidx.navigation:navigation-ui:2.8.9")
    implementation(libs.circleimageview)
    implementation(libs.picasso)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(libs.kotlinx.metadata.jvm)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")
    implementation(libs.material.v190)
}