import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
    alias(libs.plugins.ksp)
}

val localProperties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

android {
    compileSdk = 35
    namespace = "info.firozansari.movieapp"

    defaultConfig {
        applicationId = "info.firozansari.movieapp"
        targetSdk = 35
        minSdk = 26

        versionCode = 2
        versionName = "1.1"
        buildConfigField("String", "TMDB_API_KEY", "\"${localProperties.getProperty("tmdb_api_key") ?: ""}\"")


        testInstrumentationRunner = libs.versions.androidTestInstrumentation.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        dataBinding = true
        viewBinding = true
    }

    buildFeatures {
        buildConfig = true
    }
//    sourceSets.configure {
//        named("androidTest") {
//            java.srcDirs("src/test-common/java")
//        }
//        named("test") {
//            java.srcDirs("src/test-common/java")
//        }
//    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    //implementation(libs.androidx.swiperefreshlayout)
    //implementation(libs.jetbrains.kotlin.reflect)

    // Navigation Component
    implementation(libs.navigation.frag)
    implementation(libs.navigation.ui)

    // Room components
    implementation(libs.roomRuntime)
    ksp(libs.roomCompiler)
    implementation(libs.roomKotlinExt)
    //androidTestImplementation(libs.androidx.room.testing)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.lifecycle)

    //Networking
    implementation(libs.retrofit)
    //implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.httpLog)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.coroutines.adapter)
    implementation(libs.google.code.gson)

    implementation(libs.retrofit.moshi)
    api(libs.moshi)
    ksp(libs.moshiCodeGen)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    androidTestImplementation(libs.google.dagger.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)
    testImplementation(libs.google.dagger.hilt.android.testing)
    kspTest(libs.hilt.android.compiler)

    // Coil for image loading
    implementation(libs.coil)

    // Jetpack paging 3
    implementation(libs.androidx.paging.runtime.ktx)

    // JetPack DataStore
    implementation(libs.androidx.datastore.preferences)

    // Intuit ssp & sdp
    implementation(libs.intuit.sdp)
    implementation(libs.intuit.ssp)

    // Android Youtube Player
    implementation(libs.pierfrancescosoffritti.androidyoutubeplayer.core)

    // Chrome Custom Tab
    implementation(libs.androidx.browser)

    // Material Dot viewpager indicator library
    implementation(libs.github.zhpanvip.viewpagerindicator)

    // Pallet
    implementation(libs.androidx.palette.ktx)

    // debugging
    implementation(libs.jakewharton.timber)
    implementation(libs.facebook.stetho)
    implementation(libs.facebook.stetho.okhttp3)

    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.mockitoCore)

    testImplementation(libs.robolectric)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.google.truth)
}

