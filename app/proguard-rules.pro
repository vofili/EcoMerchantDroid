# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/val/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn okio.**
-keep public class OK**
-keep class com.facebook.** { *; }
-keep class okio.**
-keep class com.google.gson.**
-keep class okhttp3.**
-keep class cz.**
-keep class com.google.**
-keep class com.google.zxing.**
-keep class android.livespace.com.ecobankmerchant.dataobjects.**
-keep class android.livespace.com.ecobankmerchant.dataobjects.** { *;}
