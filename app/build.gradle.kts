import org.gradle.kotlin.dsl.libs

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.btpj.wanandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.btpj.wanandroid"
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
        debug {
            // 设置调试时包名后添加debug以防止与正式版安装冲突
            applicationIdSuffix = ".debug"

            // 假如打包后运行闪退，可以在调试模式下打开混淆查看日志找出混淆问题
            isMinifyEnabled = false
            isShrinkResources = false
            isZipAlignEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        release {
            isMinifyEnabled = true // 开启混淆
            isShrinkResources = true // 启动资源压缩
            isZipAlignEnabled = true // 开启zipalign优化
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            applicationVariants.all {
                outputs.all {
                    // 输出apk名称为：App名_版本名.apk
                    if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                        this.outputFileName = "WanAndroid_V${defaultConfig.versionName}.apk"
                    }
                }
            }
        }
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":lib_base"))

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.bugly.update)
    implementation(libs.bugly.nativecrashreport)
    implementation(libs.accompanist.webview)
    implementation(libs.accompanist.systemuicontroller)

    debugImplementation(libs.leakcanary)
}

kapt {
    correctErrorTypes = true
}