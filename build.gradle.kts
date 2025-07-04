// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // alias(libs.plugins.android.library) apply false // Commented out this line
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false

}
