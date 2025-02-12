plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.github.bumptech.glide:compiler:4.12.0")


    implementation("com.airbnb.android:lottie:5.0.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // 구글 로그인
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    //네이버 로그인
    //implementation("com.navecorp.nid:oauth:5.9.0")
    implementation(files("libs/oauth-5.9.0.aar")) //네이버 로그인 api에 필요한 모듈 추가

    //implementation ("com.kakao.sdk:v2-user:2.12.1")
    implementation ("com.kakao.sdk:v2-all:2.15.0" )// 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation ("com.kakao.sdk:v2-user:2.15.0" )// 카카오 로그인
    implementation ("com.kakao.sdk:v2-talk:2.15.0") // 친구, 메시지(카카오톡)
    implementation ("com.kakao.sdk:v2-story:2.15.0") // 카카오스토리
    implementation ("com.kakao.sdk:v2-share:2.15.0") // 메시지(카카오톡 공유)
    implementation ("com.kakao.sdk:v2-navi:2.15.0") // 카카오내비
    implementation ("com.kakao.sdk:v2-friend:2.15.0") // 카카오톡 소셜 피커, 리소스 번들 파일 포함
    implementation ("com.github.bumptech.glide:glide:4.13.2") // 사진 라이브러리에 필요함!
    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.2")
}