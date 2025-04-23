plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlin.parcelize)
}

android {
	namespace = "eu.newsapp"
	compileSdk = 35

	defaultConfig {
		applicationId = "eu.newsapp"
		minSdk = 24
		targetSdk = 35
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
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)

	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.navigation.compose)

// Koin for Android & Compose
	implementation(libs.koin.android)
	implementation(libs.koin.androidx.compose)

// Retrofit & OkHttp
	implementation(libs.retrofit)
	implementation(libs.converter.gson)
	implementation(libs.logging.interceptor)

// Coil for image loading
	implementation(libs.coil.compose)

// Shared Preferences (DataStore)
	implementation(libs.androidx.datastore.preferences)
	implementation(libs.androidx.navigation.testing)

// Unit test
	testImplementation(libs.junit)
	testImplementation(libs.kotlinx.coroutines.test)

// Android Instrumentation tests
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.core.testing)

	debugImplementation(libs.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	androidTestImplementation(libs.androidx.ui.test.junit4)
}