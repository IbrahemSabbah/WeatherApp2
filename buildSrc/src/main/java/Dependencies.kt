object Hilt {
    private const val hilt_version = "2.35.1"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:$hilt_version"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:$hilt_version"
    const val HILT_COMPILER = "androidx.hilt:hilt-compiler:1.0.0"
    const val HILT_WORKER = "androidx.hilt:hilt-work:1.0.0"

}

object Worker{
    const val WORKER_VERSION="2.6.0"
    const val WORKER_RUNTIME="androidx.work:work-runtime-ktx:$WORKER_VERSION"
}

object AndroidX {
    const val CORE_KTX = "androidx.core:core-ktx:1.6.0"
    const val APP_COMPAT = "androidx.appcompat:appcompat:1.3.1"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.1"
    const val LIFE_CYCLE_LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
    const val LIFE_CYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
}

object UIHelper {
    const val GOOGLE_MATERIAL = "com.google.android.material:material:1.4.0"
}

object Network {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:2.6.0"
    const val OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:4.9.1"
}

object RoomDB {
    private const val ROOM_VERSION = "2.3.0"
    const val ROOM_KTX = "androidx.room:room-ktx:$ROOM_VERSION"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
}

object NavigationJetpack {
    private const val NAVIGATION_VERSION = "2.3.5"
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"
}


object Security{
    const val ANDROID_CRYPTO="androidx.security:security-crypto:1.0.0"
}