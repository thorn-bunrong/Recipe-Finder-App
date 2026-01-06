plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Plugin for Room's annotation processor
    id("kotlin-kapt")
}

android {namespace = "com.example.lab2mad"
    compileSdk = 36 // Using 34 as a common stable version

    defaultConfig {
        applicationId = "com.example.lab2mad"
        minSdk = 24
        targetSdk = 36 // Should match compileSdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3" // A standard compatible version
    }
}

dependencies {
    // Core Android & Compose dependencies from libs.toml
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Onboarding
    implementation("androidx.compose.foundation:foundation:1.6.7")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.34.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // DataStore (for first launch flag)
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Image Loading
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Icons
    implementation("androidx.compose.material:material-icons-extended-android:1.6.7")

    // Room Database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // ViewModel
    val lifecycle_version = "2.8.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    // Networking (Retrofit, OkHttp, Gson)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // For the Interceptor

    // Testing dependencies from libs.toml
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
