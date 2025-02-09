plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.gonggaksim_frontend"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gonggaksim_frontend"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("~/Doucuments/UMC_프로젝트/Frontend-kariv/Frontend/app/key.jks")
            storePassword = "ems0718ems"
            keyAlias = "key"
            keyPassword = "ems0718ems"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
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
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.github.bumptech.glide:compiler:4.12.0")


    implementation("com.airbnb.android:lottie:5.0.2")

    // 구글 로그인
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("com.google.firebase:firebase-bom:33.8.0")
    implementation("com.google.firebase:firebase-analytics-license:12.0.1")
    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //네이버 로그인
    //implementation("com.navecorp.nid:oauth:5.9.0")
    implementation(files("libs/oauth-5.9.0.aar")) //네이버 로그인 api에 필요한 모듈 추가
}