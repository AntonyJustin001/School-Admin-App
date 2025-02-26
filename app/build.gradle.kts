plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "school.admin.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "school.admin.app"
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.volley)
    implementation(libs.viewpager2)
    implementation(libs.fragmentKtx)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter)
    implementation(libs.lottie)
    implementation(libs.responsive)
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseAuth)
    implementation(libs.firebaseDatabase)
    implementation(libs.fireStore)
    implementation("com.google.firebase:firebase-storage:20.1.0")
    implementation(libs.gson)
    implementation(libs.glide)
    implementation(libs.exoPlayer)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    annotationProcessor(libs.glideCompiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}