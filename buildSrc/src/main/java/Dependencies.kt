object Versions {
    const val compile_sdk = 28
    const val build_tools = "29.0.2"
    const val min_sdk = 19
    const val target_sdk = 28
    const val version_code = 1
    const val version_name = "1.0"
    const val kotlin = "1.3.50"
    const val support_lib = "28.0.0"
    const val support_constraint_lib = "1.1.3"
    const val support_design = "28.0.0"
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
    const val brvah = "2.9.50"
    const val immersionBar = "3.0.0"
    const val glide = "4.9.0"
    const val smartRefresh = "1.1.0"
    const val agentWeb = "4.1.2"
    const val picture_selector = "2.2.5"
}

object Package {
    const val application_Id = "com.df.playandroid"
}

object Libs {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val support_appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    const val support_constraint = "com.android.support.constraint:constraint-layout:${Versions.support_constraint_lib}"
    const val junit = "junit:junit:${Versions.junit_lib}"
    const val test_runner = "com.android.support.test:runner:${Versions.test_runner_lib}"
    const val espresso_core = "com.android.support.test.espresso:espresso-core:${Versions.espresso_core_lib}"
    const val support_design = "com.android.support:design:${Versions.support_design}"
    const val qmui = "com.qmuiteam:qmui:${Versions.qumi}"
    const val okhttp3_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3_logging}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2_converters = "com.squareup.retrofit2:retrofit-converters:${Versions.retrofit2}"
    const val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val retrofit2_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxjava2}"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val BRVAH = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.brvah}"
    const val ImmersionBar = "com.gyf.immersionbar:immersionbar:${Versions.immersionBar}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val smartRefresh = "com.scwang.smartrefresh:SmartRefreshLayout:${Versions.smartRefresh}"
    const val agentWeb = "com.just.agentweb:agentweb:${Versions.agentWeb}"
    const val picture_selector = "com.github.LuckSiege.PictureSelector:picture_library:v${Versions.picture_selector}"
}