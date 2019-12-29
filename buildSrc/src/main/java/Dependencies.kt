object Versions {
    const val compileSdkVersion = 28
    const val buildToolsVersion = "29.0.2"
    const val minSdkVersion = 19
    const val targetSdkVersion = 28
    const val versionCode = 1
    const val versionName = "1.0"

    const val support_lib = "28.0.0"
    const val support_constraint_lib = "1.1.3"
    const val junit_lib = "4.12"
    const val test_runner_lib = "1.0.2"
    const val espresso_core_lib = "3.0.2"


    const val qumi = "1.4.4"
    const val okhttp3_logging = "3.10.0"
    const val gson = "2.8.5"
    const val okhttp3 = "3.14.4"
    const val retrofit2 = "2.7.0"
    const val rxjava2 = "2.2.16"
    const val rxandroid = "2.1.1"
}

object Package {
    const val applicationId = "com.df.playandroid"
}

object Libs {
    val support_appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    val support_constraint = "com.android.support.constraint:constraint-layout:${Versions.support_constraint_lib}"
    val junit = "junit:junit:${Versions.junit_lib}"
    val test_runner = "com.android.support.test:runner:${Versions.test_runner_lib}"
    val espresso_core = "com.android.support.test.espresso:espresso-core:${Versions.espresso_core_lib}"
    val qmui = "com.qmuiteam:qmui:${Versions.qumi}"
    val okhttp3_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3_logging}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    val retrofit2_converters = "com.squareup.retrofit2:retrofit-converters:${Versions.retrofit2}"
    val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    val retrofit2_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
    val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxjava2}"
    val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
}