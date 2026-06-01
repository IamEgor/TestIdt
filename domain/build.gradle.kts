plugins { alias(libs.plugins.android.library) }

android {
    namespace = "by.idt.testapp.domain"
    compileSdk = 37
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.koin.core)
    testImplementation(libs.junit)
}
