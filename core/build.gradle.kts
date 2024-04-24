plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("kotlin-parcelize")
	id("com.google.dagger.hilt.android")
	id("com.google.devtools.ksp")
}

apply(from = "../shared_dependencies.gradle")

android {
	namespace = "com.quiraadev.core"
	compileSdk = 34

	defaultConfig {
		minSdk = 26
//		buildConfigField("String", "TOKEN", "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZDI1ZGRlODViODE3ZmNiOTA5NDI0ODE3NTI2ZjNlMCIsInN1YiI6IjY1MDk0MjNjM2NkMTJjMDE0ZWMwOTAzNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.z03M-jOeNhNNuuZGHs03_RDdGBAXnMd9M0Tb9xD9xZI")
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
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
		buildConfig = true
	}
}

dependencies {

	val retrofitVersion = "2.9.0"
	val loggingInterceptorVersion = "4.12.0"
	val coroutinesVersion = "1.6.1"
	val roomVersion = "2.6.1"

//	Networking
	implementation("com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion")
	implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
	implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")

//  Room Database

	implementation("androidx.room:room-runtime:$roomVersion")
	ksp("androidx.room:room-compiler:$roomVersion")
	implementation("androidx.room:room-ktx:$roomVersion")
	testImplementation("androidx.room:room-testing:$roomVersion")

//	Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
	api("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

	implementation("net.zetetic:android-database-sqlcipher:4.4.0")
	implementation("androidx.sqlite:sqlite-ktx:2.4.0")

	//Testing

	//special testing - for testing livedatas
	testImplementation("androidx.arch.core:core-testing:2.2.0")

	// Coroutine test
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")

	//mockito - untuk membuat mocking
	testImplementation("org.mockito:mockito-core:4.8.1")
	testImplementation("org.mockito:mockito-inline:4.8.1")

	// InstantTaskExecutorRule
	testImplementation("androidx.arch.core:core-testing:2.2.0")
}