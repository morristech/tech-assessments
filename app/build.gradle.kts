import java.util.*

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("io.gitlab.arturbosch.detekt")
}

android {
    compileSdkVersion(Versions.COMPILE_SDK)
    buildToolsVersion("30.0.3")
    defaultConfig {
        applicationId = "design.morristech.openweather"
        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.TARGET_SDK)
        versionCode = Versions.VERSION_CODE
        versionName = Versions.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resValue(
            "string",
            "google_maps_key",
            "AIzaSyBeYujT9sHHv1bGWc1SwHCraDFicL1zqAI"
        )
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            versionNameSuffix = "-debug"
            isShrinkResources = false
            isMinifyEnabled = false
        }
    }

    buildTypes.forEach {
        // Loading secret API keys from local.properties to BuildConfig
        val properties = Properties().apply {
            load(project.rootProject.file("local.properties").inputStream())
        }
        val openWeatherMapBaseUrl: String = properties.getProperty("openWeatherMap.baseUrl", "")
        val openWeatherMapApiKey: String = properties.getProperty("openWeatherMap.apiKey", "")
        val googleMapsApiKey: String = properties.getProperty("google.maps.apiKey", "")
        it.buildConfigField("String", "API_KEY_OPEN_WEATHER_MAP", "\"${openWeatherMapApiKey}\"")
        it.buildConfigField("String", "API_BASE_URL", "\"${openWeatherMapBaseUrl}\"")
        it.resValue(
            "string",
            "google_maps_key",
            googleMapsApiKey
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                //arguments = mapOf("room.incremental" to "true")
            }
        }
    }
    buildFeatures {
        viewBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.KOTLIN)
    implementation(Libs.COROUTINES)

    // UI
    implementation(Libs.MATERIAL_DIALOGS)
    implementation(Libs.MATERIAL)
    implementation(Libs.ANDROID_SUPPORT)
    implementation(Libs.RECYCLERVIEW)
    implementation(Libs.APPCOMPAT)
    implementation(Libs.SWIPEREFRESH_LAYOUT)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.INTERPOLATORS)

    // Tests
    testImplementation(Libs.JUNIT)
    androidTestImplementation(Libs.ARCH_TESTING)
    androidTestImplementation(Libs.JUNIT)
    androidTestImplementation(Libs.TEST_RUNNER)
    androidTestImplementation(Libs.TEST_RULES)
    androidTestImplementation(Libs.ESPRESSO)

    // Architecture Components
    implementation(Libs.KTX_CORE)
    implementation(Libs.KTX_FRAGMENT)
    implementation(Libs.KTX_PREFERENCE)
    implementation(Libs.KTX_LIFECYCLE_VIEWMODEL)
    implementation(Libs.KTX_LIFECYCLE_LIVEDATA)
    implementation(Libs.KTX_LIFECYCLE_EXTENSIONS)

    // Google Play Services
    implementation(Libs.PLAY_SERVICES_LOCATION)
    implementation(Libs.PLAY_SERVICES_MAPS)

    // Network
    implementation(Libs.RETROFIT2)
    implementation(Libs.OK_HTTP_LOGGER)
    implementation(Libs.MOSHI)
    implementation(Libs.RETROFIT2_MOSHI_CONVERTER)
    kapt(Libs.MOSHI_CODE_GEN)

    // Room - Database
    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.KTX_ROOM)
    kapt(Libs.ROOM_COMPILER)

    // Hilt - Dependency Injection
    implementation(Libs.HILT)
    kapt(Libs.HILT_COMPILER)
}

apply(plugin = "com.google.gms.google-services")
