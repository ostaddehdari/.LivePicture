plugins {
    id("com.android.application")
}

android {
    namespace = "cn.easyar.samples.helloarvideo"
    compileSdk = 35

    defaultConfig {
        // باید دقیقاً با Package Name ثبت‌شده در سایت EasyAR یکی باشد، وگرنه کلید لایسنس کار نمی‌کند
        applicationId = "com.miss.hashemi"
        minSdk = 24
        targetSdk = 35
        // بالاتر از نسخهٔ ARCore/OpenCV (۱) تا روی آن نصب شود
        versionCode = 2
        versionName = "2.0-easyar"
        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a")
        }
    }

    // همان کلید امضای نسخهٔ قبلی miss hashemi
    signingConfigs {
        create("hashemi") {
            storeFile = file("hashemi.keystore")
            storePassword = "hashemi123"
            keyAlias = "hashemi"
            keyPassword = "hashemi123"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("hashemi")
        }
        release {
            signingConfig = signingConfigs.getByName("hashemi")
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

    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

dependencies {
    // EasyAR Sense 4.11 — به‌جای EasyAR.aar (بیش از ۲۵ مگابایت، قابل آپلود در GitHub نیست)
    // کلاس‌ها در libs/EasyAR.jar و کتابخانه‌های بومی در src/main/jniLibs قرار دارند
    implementation(files("libs/EasyAR.jar"))
}
