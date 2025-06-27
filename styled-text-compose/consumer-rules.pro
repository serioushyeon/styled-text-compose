# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep all public API classes and methods
-keep public class dev.serious.styledtext.** { public *; }

# Keep Compose related classes
-keep class androidx.compose.** { *; }
-keep class kotlin.** { *; }

# Keep extension functions
-keepclassmembers class kotlin.** {
    public static *** styled(...);
    public static *** bold(...);
    public static *** highlight(...);
    public static *** colorize(...);
    public static *** clickable(...);
}