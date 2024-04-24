plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-parcelize")
	id("com.google.dagger.hilt.android")
	id("com.google.devtools.ksp")
	id("com.google.gms.google-services")
	id("com.google.firebase.crashlytics")
}

apply(from = "../shared_dependencies.gradle")

android {
	namespace = "com.quiraadev.moviemate"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.quiraadev.moviemate"
		minSdk = 26
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}

		debug {
			isMinifyEnabled = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
		viewBinding = true
	}
	dynamicFeatures += setOf(":favorite")
}

dependencies {
	implementation(project(":core"))
	implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.7")
	implementation("com.google.firebase:firebase-crashlytics:18.6.4")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.11.0")
	implementation("androidx.activity:activity:1.9.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")
}