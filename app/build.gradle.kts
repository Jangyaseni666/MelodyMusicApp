plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.exoplayer)
    implementation (libs.logging.interceptor)
    implementation(libs.androidx.recyclerview)
    implementation (libs.picasso)
    implementation (libs.ui)
    implementation (libs.material3)
    implementation (libs.androidx.activity.compose)
    implementation (libs.coil.compose)
    implementation (libs.material3)
    implementation (libs.google.accompanist.systemuicontroller)
    implementation (libs.accompanist.navigation.material)
    implementation (libs.androidx.material)
    implementation (libs.ui)
    implementation (libs.androidx.foundation)
    implementation (libs.androidx.runtime)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.activity.compose)
    implementation (libs.coil.compose)
    implementation (libs.androidx.media3.exoplayer)
    implementation (libs.androidx.media3.ui)
    implementation (libs.androidx.navigation.compose)

    implementation("androidx.compose.animation:animation:1.5.1") // Align with Compose version
    implementation("androidx.navigation:navigation-compose:2.5.0")
    implementation("com.google.dagger:hilt-android:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    //kapt("com.google.dagger:hilt-compiler:2.45")
    implementation("androidx.compose.runtime:runtime-livedata:1.3.0")

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Lifecycle Dependencies
    val lifecycle_version = "2.8.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    implementation("com.google.android.gms:play-services-auth:12.0.1")
    implementation("io.coil-kt:coil-compose:2.2.2")


}