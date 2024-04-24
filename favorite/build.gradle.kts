plugins {
	id("com.android.dynamic-feature")
	id("org.jetbrains.kotlin.android")
	id("com.google.devtools.ksp")
	id("com.google.dagger.hilt.android")
}

apply(from = "../shared_dependencies.gradle")

android {
	namespace = "com.quiraadev.moviemate.favorite"
	compileSdk = 34

	defaultConfig {
		minSdk = 26
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
		}

		debug {
			isMinifyEnabled = false
		}

	}

	buildFeatures {
		viewBinding = true
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
	implementation(project(":app"))
	implementation(project(":core"))
}
