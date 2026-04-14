# Add project specific ProGuard rules here.
-keep class com.budget.manager.data.model.** { *; }
-keep class com.budget.manager.data.local.dao.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Room
-keep class * extends androidx.room.RoomDatabase { *; }
-dontwarn androidx.room.**

# Hilt
-keepclassmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}
