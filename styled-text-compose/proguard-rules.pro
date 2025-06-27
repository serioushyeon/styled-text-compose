# Add project specific ProGuard rules here.

# Kotlin
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep our public API
-keep public class dev.serious.styledtext.** {
    public *;
}

# Keep data classes
-keep class dev.serious.styledtext.TextStyleConfig { *; }
-keep class dev.serious.styledtext.StyleRule { *; }

# Keep enum classes
-keepclassmembers enum com.yourpackage.styledtext.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}