plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")  // ← ADICIONE ESTA LINHA
    kotlin("kapt")
}

android {
    namespace = "com.exemplo.gerenciamentoconvidados"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.exemplo.gerenciamentoconvidados"
        minSdk = 24
        targetSdk = 36
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
    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    // Compose (versões atualizadas para Kotlin 2.0)
    implementation("androidx.compose.ui:ui:1.6.0")  // Ou 1.7.x se disponível
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Room (mantenha ou atualize se houver incompatibilidade)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // ViewModel e LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")

    // Outros
    implementation("androidx.core:core-ktx:1.13.0")
}